package com.example.rally.repository;

import java.nio.file.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.example.rally.model.Feature;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Repository
public class JsonFeatureRepository implements FeatureRepository {

	private final JsonMapper mapper;
	private final Path path;

	public JsonFeatureRepository(JsonMapper mapper, @Value("${rally.data-dir}") String dataDir) {

		this.mapper = mapper;
		this.path = Path.of(dataDir, "metadata", "features.json");
	}

	@Override
	public synchronized void save(Feature feature) {

		try {

			Files.createDirectories(path.getParent());

			List<Feature> items = Files.exists(path)
					? mapper.readValue(path.toFile(), new TypeReference<List<Feature>>() {
					})
					: new ArrayList<>();

			items.removeIf(x -> Objects.equals(x.featureNumber(), feature.featureNumber()));

			items.add(feature);

			mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), items);

		} catch (Exception e) {

			throw new IllegalStateException("Unable to save metadata", e);
		}
	}

	@Override
	public synchronized List<Feature> findAll() {

		try {

			if (!Files.exists(path)) {
				return new ArrayList<>();
			}

			return mapper.readValue(path.toFile(), new TypeReference<List<Feature>>() {
			});

		} catch (Exception e) {

			throw new IllegalStateException("Unable to read feature metadata", e);
		}
	}

	@Override
	public Optional<Feature> findById(String featureNumber) {

		return findAll().stream().filter(feature -> Objects.equals(feature.featureNumber(), featureNumber)).findFirst();
	}
}