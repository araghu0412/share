package com.example.rally.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class LocalSemanticEmbeddingService implements EmbeddingService {

	private static final String MODEL_NAME = "sentence-transformers/all-MiniLM-L6-v2";

	private final EmbeddingModel embeddingModel;

	public LocalSemanticEmbeddingService(EmbeddingModel embeddingModel) {
		this.embeddingModel = embeddingModel;
	}

	@Override
	public List<Double> generate(String text) {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("Text cannot be blank when generating an embedding");
		}

		float[] values = embeddingModel.embed(text);
		List<Double> embedding = new ArrayList<>(values.length);

		for (float value : values) {
			embedding.add((double) value);
		}

		return embedding;
	}

	@Override
	public String modelName() {
		return MODEL_NAME;
	}
}
