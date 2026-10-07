package com.example.rally.handler;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.rally.agents.model.*;
import com.example.rally.model.*;
import com.example.rally.repository.*;
import com.example.rally.service.*;

@Component
public class HybridQueryHandler {
	private static final int TOP_K = 5;
	@Value("${rally.search.minimum-similarity-score:0.40}")
	private double minimumSimilarityScore;
	private final EmbeddingService embeddings;
	private final VectorRepository vectors;
	private final FeatureRepository features;
	private final UserStoryRepository stories;
	private final DefectRepository defects;
	private final RagGenerationService generator;

	public HybridQueryHandler(EmbeddingService embeddings, VectorRepository vectors, FeatureRepository features,
			UserStoryRepository stories, DefectRepository defects, RagGenerationService generator) {
		this.embeddings = embeddings;
		this.vectors = vectors;
		this.features = features;
		this.stories = stories;
		this.defects = defects;
		this.generator = generator;
	}

	public Object handle(String question, RouteDecision d) {
		// HYBRID also covers structured filtering + generation (for example release
		// summaries).
		// Semantic retrieval is used only when searchText is actually present.
		if (d.searchText() == null || d.searchText().isBlank()) {
			return switch (d.entityType()) {
			case FEATURE -> generator.generateFeatureRecordsAnswer(question, filteredFeatures(d));
			case USER_STORY -> generator.generateUserStoryRecordsAnswer(question, filteredStories(d));
			case DEFECT -> generator.generateDefectRecordsAnswer(question, filteredDefects(d));
			};
		}
		List<Double> qv = embeddings.generate(d.searchText());
		return switch (d.entityType()) {
		case FEATURE -> generator.generateFeatureAnswer(question, feature(qv, d));
		case USER_STORY -> generator.generateUserStoryAnswer(question, story(qv, d));
		case DEFECT -> generator.generateDefectAnswer(question, defect(qv, d));
		};
	}

	private List<Feature> filteredFeatures(RouteDecision d) {
		return features.findAll().stream()
				.filter(x -> m(v(d, "release"), x.release()) && m(v(d, "featureSize"), x.featureSize())
						&& m(v(d, "epicNumber"), x.epicNumber()) && m(v(d, "featureNumber"), x.featureNumber()))
				.toList();
	}

	private List<UserStory> filteredStories(RouteDecision d) {
		return stories.findAll().stream()
				.filter(x -> m(v(d, "release"), x.release()) && m(v(d, "featureNumber"), x.featureNumber())
						&& m(v(d, "userStoryProgress"), x.userStoryProgress())
						&& mi(v(d, "storyPoints"), x.storyPoints()) && m(v(d, "userStoryNumber"), x.userStoryNumber()))
				.toList();
	}

	private List<Defect> filteredDefects(RouteDecision d) {
		return defects.findAll().stream()
				.filter(x -> m(v(d, "release"), x.release()) && m(v(d, "userStoryNumber"), x.userStoryNumber())
						&& m(v(d, "defectProgress"), x.defectProgress()) && mi(v(d, "defectPoints"), x.defectPoints())
						&& m(v(d, "defectNumber"), x.defectNumber()))
				.toList();
	}

	private List<FeatureSearchResult> feature(List<Double> q, RouteDecision d) {
		return vectors.findAll().stream().filter(x -> "FEATURE".equalsIgnoreCase(x.type()))
				.map(x -> features.findById(x.id())
						.filter(f -> m(v(d, "release"), f.release()) && m(v(d, "featureSize"), f.featureSize())
								&& m(v(d, "epicNumber"), f.epicNumber()) && m(v(d, "featureNumber"), f.featureNumber()))
						.map(f -> new FeatureSearchResult(f, cosine(q, x.embedding()))).orElse(null))
				.filter(Objects::nonNull).filter(x -> x.score() >= minimumSimilarityScore)
				.sorted((a, b) -> Double.compare(b.score(), a.score())).limit(TOP_K).toList();
	}

	private List<UserStorySearchResult> story(List<Double> q, RouteDecision d) {
		return vectors.findAll().stream().filter(x -> "USER_STORY".equalsIgnoreCase(x.type())).map(x -> stories
				.findById(x.id())
				.filter(s -> m(v(d, "release"), s.release()) && m(v(d, "featureNumber"), s.featureNumber())
						&& m(v(d, "userStoryProgress"), s.userStoryProgress())
						&& mi(v(d, "storyPoints"), s.storyPoints()) && m(v(d, "userStoryNumber"), s.userStoryNumber()))
				.map(s -> new UserStorySearchResult(s, cosine(q, x.embedding()))).orElse(null)).filter(Objects::nonNull)
				.filter(x -> x.score() >= minimumSimilarityScore).sorted((a, b) -> Double.compare(b.score(), a.score()))
				.limit(TOP_K).toList();
	}

	private List<DefectSearchResult> defect(List<Double> q, RouteDecision d) {
		return vectors.findAll().stream().filter(x -> "DEFECT".equalsIgnoreCase(x.type())).map(x -> defects
				.findById(x.id())
				.filter(df -> m(v(d, "release"), df.release()) && m(v(d, "userStoryNumber"), df.userStoryNumber())
						&& m(v(d, "defectProgress"), df.defectProgress()) && mi(v(d, "defectPoints"), df.defectPoints())
						&& m(v(d, "defectNumber"), df.defectNumber()))
				.map(df -> new DefectSearchResult(df, cosine(q, x.embedding()))).orElse(null)).filter(Objects::nonNull)
				.filter(x -> x.score() >= minimumSimilarityScore).sorted((a, b) -> Double.compare(b.score(), a.score()))
				.limit(TOP_K).toList();
	}

	private List<String> v(RouteDecision d, String k) {
		return d.filters() == null ? null : d.filters().get(k);
	}

	private boolean m(List<String> q, String actual) {
		return q == null || q.isEmpty()
				|| q.stream().anyMatch(x -> x != null && actual != null && x.equalsIgnoreCase(actual));
	}

	private boolean mi(List<String> q, Integer actual) {
		return q == null || q.isEmpty()
				|| q.stream().anyMatch(x -> x != null && actual != null && x.equals(String.valueOf(actual)));
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
