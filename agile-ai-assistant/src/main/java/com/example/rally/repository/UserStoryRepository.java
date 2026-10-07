package com.example.rally.repository;

import java.util.List;
import java.util.Optional;
import com.example.rally.model.UserStory;

public interface UserStoryRepository {
	void save(UserStory userStory);

	List<UserStory> findAll();

	Optional<UserStory> findById(String userStoryNumber);
}
