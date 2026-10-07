package com.example.rally.service;

import org.springframework.stereotype.Service;
import com.example.rally.dto.UserStoryRequest;
import com.example.rally.model.*;
import com.example.rally.repository.*;

@Service
public class UserStoryService {
	private final UserStoryRepository repo;
	private final VectorRepository vectors;
	private final EmbeddingService embeddings;

	public UserStoryService(UserStoryRepository repo, VectorRepository vectors, EmbeddingService embeddings) {
		this.repo = repo;
		this.vectors = vectors;
		this.embeddings = embeddings;
	}

	public void save(UserStoryRequest r) {
		UserStory s = new UserStory(r.userStoryNumber(), r.featureNumber(), r.userStoryName(), r.userStoryDescription(),
				r.startDate(), r.endDate(), r.storyPoints(), r.release(), r.userStoryProgress());
		String text = "User Story Name: " + nz(r.userStoryName()) + "\nUser Story Description: "
				+ nz(r.userStoryDescription());
		VectorRecord v = new VectorRecord(r.userStoryNumber(), "USER_STORY", embeddings.modelName(), text,
				embeddings.generate(text));
		repo.save(s);
		vectors.save(v);
	}

	private String nz(String s) {
		return s == null ? "" : s;
	}
}
