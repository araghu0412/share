package com.example.rally.repository;

import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.example.rally.model.VectorRecord;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Repository
public class JsonVectorRepository implements VectorRepository {
	private final JsonMapper mapper;
	private final Path vectorDir;

	public JsonVectorRepository(JsonMapper mapper, @Value("${rally.data-dir}") String dataDir) {
		this.mapper = mapper;
		this.vectorDir = Path.of(dataDir, "vectors");
	}

	private Path pathFor(String type) {
		return switch (type.toUpperCase()) {
		case "FEATURE" -> vectorDir.resolve("feature-vectors.json");
		case "USER_STORY" -> vectorDir.resolve("user-story-vectors.json");
		case "DEFECT" -> vectorDir.resolve("defect-vectors.json");
		default -> throw new IllegalArgumentException("Unsupported vector type: " + type);
		};
	}

	public synchronized void save(VectorRecord v) {
		Path path = pathFor(v.type());
		try {
			Files.createDirectories(path.getParent());
			List<VectorRecord> items = Files.exists(path)
					? mapper.readValue(path.toFile(), new TypeReference<List<VectorRecord>>() {
					})
					: new ArrayList<>();
			items.removeIf(x -> Objects.equals(x.id(), v.id()));
			items.add(v);
			mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), items);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save vector", e);
		}
	}

	public synchronized List<VectorRecord> findAll() {
		List<VectorRecord> all = new ArrayList<>();
		for (String type : List.of("FEATURE", "USER_STORY", "DEFECT")) {
			Path path = pathFor(type);
			try {
				if (Files.exists(path))
					all.addAll(mapper.readValue(path.toFile(), new TypeReference<List<VectorRecord>>() {
					}));
			} catch (Exception e) {
				throw new IllegalStateException("Unable to read vectors", e);
			}
		}
		return all;
	}
}
