package com.example.rally.handler;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.rally.agents.model.*;
import com.example.rally.model.*;
import com.example.rally.repository.*;
import com.example.rally.service.*;

@Component
public class RagQueryHandler {
	private static final int TOP_K = 5;
	@Value("${rally.search.minimum-similarity-score:0.40}")
	private double minimumSimilarityScore;
	private final EmbeddingService embeddings;
	private final VectorRepository vectors;
	private final FeatureRepository features;
	private final UserStoryRepository stories;
	private final DefectRepository defects;
	private final RagGenerationService generator;

	public RagQueryHandler(EmbeddingService embeddings, VectorRepository vectors, FeatureRepository features,
			UserStoryRepository stories, DefectRepository defects, RagGenerationService generator) {
		this.embeddings = embeddings;
		this.vectors = vectors;
		this.features = features;
		this.stories = stories;
		this.defects = defects;
		this.generator = generator;
	}

	public Object handle(String q, RouteDecision d) {
		String s = d.searchText();
		if (s == null || s.isBlank())
			throw new IllegalArgumentException("searchText is required for RAG search");
		List<Double> qv = embeddings.generate(s);
		return switch (d.entityType()) {
		case FEATURE -> generator.generateFeatureAnswer(q, feature(qv));
		case USER_STORY -> generator.generateUserStoryAnswer(q, story(qv));
		case DEFECT -> generator.generateDefectAnswer(q, defect(qv));
		};
	}

	private List<FeatureSearchResult> feature(List<Double> q) {
		return vectors.findAll().stream().filter(v -> "FEATURE".equalsIgnoreCase(v.type()))
				.map(v -> features.findById(v.id()).map(x -> new FeatureSearchResult(x, cosine(q, v.embedding())))
						.orElse(null))
				.filter(Objects::nonNull).filter(x -> x.score() >= minimumSimilarityScore)
				.sorted((a, b) -> Double.compare(b.score(), a.score())).limit(TOP_K).toList();
	}

	private List<UserStorySearchResult> story(List<Double> q) {
		return vectors.findAll().stream().filter(v -> "USER_STORY".equalsIgnoreCase(v.type()))
				.map(v -> stories.findById(v.id()).map(x -> new UserStorySearchResult(x, cosine(q, v.embedding())))
						.orElse(null))
				.filter(Objects::nonNull).filter(x -> x.score() >= minimumSimilarityScore)
				.sorted((a, b) -> Double.compare(b.score(), a.score())).limit(TOP_K).toList();
	}

	private List<DefectSearchResult> defect(List<Double> q) {
		return vectors.findAll().stream().filter(v -> "DEFECT".equalsIgnoreCase(v.type()))
				.map(v -> defects.findById(v.id()).map(x -> new DefectSearchResult(x, cosine(q, v.embedding())))
						.orElse(null))
				.filter(Objects::nonNull).filter(x -> x.score() >= minimumSimilarityScore)
				.sorted((a, b) -> Double.compare(b.score(), a.score())).limit(TOP_K).toList();
	}

	private double cosine(List<Double> a, List<Double> b) {
		if (a.size() != b.size())
			throw new IllegalArgumentException("Vector dimensions do not match");
		double dot = 0, ma = 0, mb = 0;
		for (int i = 0; i < a.size(); i++) {
			double x = a.get(i), y = b.get(i);
			dot += x * y;
			ma += x * x;
			mb += y * y;
		}
		return ma == 0 || mb == 0 ? 0 : dot / (Math.sqrt(ma) * Math.sqrt(mb));
	}
}
