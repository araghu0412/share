package com.example.rally.repository;

import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import com.example.rally.model.UserStory;

@Repository
public class JsonUserStoryRepository implements UserStoryRepository {
	private final JsonMapper mapper;
	private final Path path;

	public JsonUserStoryRepository(JsonMapper mapper, @Value("${rally.data-dir}") String dataDir) {
		this.mapper = mapper;
		this.path = Path.of(dataDir, "metadata", "user-stories.json");
	}

	public synchronized void save(UserStory item) {
		try {
			Files.createDirectories(path.getParent());
			List<UserStory> items = Files.exists(path)
					? mapper.readValue(path.toFile(), new TypeReference<List<UserStory>>() {
					})
					: new ArrayList<>();
			items.removeIf(x -> Objects.equals(x.userStoryNumber(), item.userStoryNumber()));
			items.add(item);
			mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), items);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save user story metadata", e);
		}
	}

	public synchronized List<UserStory> findAll() {
		try {
			if (!Files.exists(path))
				return new ArrayList<>();
			return mapper.readValue(path.toFile(), new TypeReference<List<UserStory>>() {
			});
		} catch (Exception e) {
			throw new IllegalStateException("Unable to read user story metadata", e);
		}
	}

	public Optional<UserStory> findById(String id) {
		return findAll().stream().filter(x -> Objects.equals(x.userStoryNumber(), id)).findFirst();
	}
}
