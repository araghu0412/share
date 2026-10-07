package com.example.rally.service;

import java.util.List;

public interface EmbeddingService {
	List<Double> generate(String text);

	String modelName();
}
