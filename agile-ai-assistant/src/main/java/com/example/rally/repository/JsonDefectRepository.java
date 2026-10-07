package com.example.rally.repository;

import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import com.example.rally.model.Defect;

@Repository
public class JsonDefectRepository implements DefectRepository {
	private final JsonMapper mapper;
	private final Path path;

	public JsonDefectRepository(JsonMapper mapper, @Value("${rally.data-dir}") String dataDir) {
		this.mapper = mapper;
		this.path = Path.of(dataDir, "metadata", "defects.json");
	}

	public synchronized void save(Defect item) {
		try {
			Files.createDirectories(path.getParent());
			List<Defect> items = Files.exists(path)
					? mapper.readValue(path.toFile(), new TypeReference<List<Defect>>() {
					})
					: new ArrayList<>();
			items.removeIf(x -> Objects.equals(x.defectNumber(), item.defectNumber()));
			items.add(item);
			mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), items);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save defect metadata", e);
		}
	}

	public synchronized List<Defect> findAll() {
		try {
			if (!Files.exists(path))
				return new ArrayList<>();
			return mapper.readValue(path.toFile(), new TypeReference<List<Defect>>() {
			});
		} catch (Exception e) {
			throw new IllegalStateException("Unable to read defect metadata", e);
		}
	}

	public Optional<Defect> findById(String id) {
		return findAll().stream().filter(x -> Objects.equals(x.defectNumber(), id)).findFirst();
	}
}
