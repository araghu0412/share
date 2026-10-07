package com.example.rally.service;

import org.springframework.stereotype.Service;

import com.example.rally.dto.FeatureRequest;
import com.example.rally.model.Feature;
import com.example.rally.model.VectorRecord;
import com.example.rally.repository.JsonFeatureRepository;
import com.example.rally.repository.JsonVectorRepository;

@Service
public class FeatureService {

	private final JsonFeatureRepository featureRepository;
	private final JsonVectorRepository vectorRepository;
	private final EmbeddingService embeddingService;

	public FeatureService(JsonFeatureRepository featureRepository, JsonVectorRepository vectorRepository,
			EmbeddingService embeddingService) {
		this.featureRepository = featureRepository;
		this.vectorRepository = vectorRepository;
		this.embeddingService = embeddingService;
	}

	public void save(FeatureRequest r) {

		Feature feature = new Feature(r.featureNumber(), r.epicNumber(), r.featureHeading(), r.featureDescription(),
				r.startDate(), r.endDate(), r.featureSize(), r.release());

		// Only semantic/descriptive content is embedded.
		// Exact fields such as release, dates and IDs stay in metadata for filtering.
		String text = buildEmbeddingText(r);

		// Generate the real semantic vector before writing either JSON file.
		// This avoids saving metadata when embedding generation fails.
		VectorRecord vector = new VectorRecord(r.featureNumber(), "FEATURE", embeddingService.modelName(), text,
				embeddingService.generate(text));

		featureRepository.save(feature);
		vectorRepository.save(vector);
	}

	private String buildEmbeddingText(FeatureRequest r) {
		StringBuilder b = new StringBuilder();
		append(b, "Feature Heading", r.featureHeading());
		append(b, "Feature Description", r.featureDescription());
		return b.toString();
	}

	private void append(StringBuilder b, String label, String value) {
		if (value != null && !value.isBlank()) {
			b.append(label).append(": ").append(value).append("\n");
		}
	}
}
