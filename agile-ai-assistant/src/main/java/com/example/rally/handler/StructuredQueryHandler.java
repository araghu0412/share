package com.example.rally.handler;

import java.util.List;
import org.springframework.stereotype.Component;
import com.example.rally.agents.model.*;
import com.example.rally.model.*;
import com.example.rally.repository.*;

@Component
public class StructuredQueryHandler {
	private final FeatureRepository features;
	private final UserStoryRepository stories;
	private final DefectRepository defects;

	public StructuredQueryHandler(FeatureRepository features, UserStoryRepository stories, DefectRepository defects) {
		this.features = features;
		this.stories = stories;
		this.defects = defects;
	}

	public Object handle(RouteDecision d) {
		return switch (d.entityType()) {
		case FEATURE -> feature(d);
		case USER_STORY -> story(d);
		case DEFECT -> defect(d);
		};
	}

	private Object feature(RouteDecision d) {
		return switch (d.operation()) {
		case COUNT -> filterFeatures(d).size();
		case GET -> getFeatures(d);
		case LIST -> filterFeatures(d);
		default -> throw unsupported(d);
		};
	}

	private Object story(RouteDecision d) {
		return switch (d.operation()) {
		case COUNT -> filterStories(d).size();
		case GET -> getStories(d);
		case LIST -> filterStories(d);
		default -> throw unsupported(d);
		};
	}

	private Object defect(RouteDecision d) {
		return switch (d.operation()) {
		case COUNT -> filterDefects(d).size();
		case GET -> getDefects(d);
		case LIST -> filterDefects(d);
		default -> throw unsupported(d);
		};
	}

	private Object getFeatures(RouteDecision d) {
		List<String> ids = required(d, "featureNumber");
		List<Feature> found = ids.stream().map(id -> features.findById(id).orElse(null)).filter(x -> x != null)
				.toList();
		if (found.isEmpty())
			throw new IllegalArgumentException("Feature not found");
		return found.size() == 1 ? found.getFirst() : found;
	}

	private Object getStories(RouteDecision d) {
		List<String> ids = required(d, "userStoryNumber");
		List<UserStory> found = ids.stream().map(id -> stories.findById(id).orElse(null)).filter(x -> x != null)
				.toList();
		if (found.isEmpty())
			throw new IllegalArgumentException("User story not found");
		return found.size() == 1 ? found.getFirst() : found;
	}

	private Object getDefects(RouteDecision d) {
		List<String> ids = required(d, "defectNumber");
		List<Defect> found = ids.stream().map(id -> defects.findById(id).orElse(null)).filter(x -> x != null).toList();
		if (found.isEmpty())
			throw new IllegalArgumentException("Defect not found");
		return found.size() == 1 ? found.getFirst() : found;
	}

	public List<Feature> filterFeatures(RouteDecision d) {
		return features.findAll().stream()
				.filter(x -> matches(values(d, "release"), x.release())
						&& matches(values(d, "featureSize"), x.featureSize())
						&& matches(values(d, "epicNumber"), x.epicNumber())
						&& matches(values(d, "featureNumber"), x.featureNumber()))
				.toList();
	}

	public List<UserStory> filterStories(RouteDecision d) {
		return stories.findAll().stream()
				.filter(x -> matches(values(d, "release"), x.release())
						&& matches(values(d, "featureNumber"), x.featureNumber())
						&& matches(values(d, "userStoryProgress"), x.userStoryProgress())
						&& matchesInteger(values(d, "storyPoints"), x.storyPoints())
						&& matches(values(d, "userStoryNumber"), x.userStoryNumber()))
				.toList();
	}

	public List<Defect> filterDefects(RouteDecision d) {
		return defects.findAll().stream()
				.filter(x -> matches(values(d, "release"), x.release())
						&& matches(values(d, "userStoryNumber"), x.userStoryNumber())
						&& matches(values(d, "defectProgress"), x.defectProgress())
						&& matchesInteger(values(d, "defectPoints"), x.defectPoints())
						&& matches(values(d, "defectNumber"), x.defectNumber()))
				.toList();
	}

	private List<String> values(RouteDecision d, String key) {
		return d.filters() == null ? null : d.filters().get(key);
	}

	private List<String> required(RouteDecision d, String key) {
		List<String> v = values(d, key);
		if (v == null || v.isEmpty())
			throw new IllegalArgumentException(key + " is required");
		return v;
	}

	private boolean matches(List<String> requested, String actual) {
		return requested == null || requested.isEmpty()
				|| requested.stream().anyMatch(v -> v != null && actual != null && v.equalsIgnoreCase(actual));
	}

	private boolean matchesInteger(List<String> requested, Integer actual) {
		return requested == null || requested.isEmpty()
				|| requested.stream().anyMatch(v -> v != null && actual != null && v.equals(String.valueOf(actual)));
	}

	private IllegalArgumentException unsupported(RouteDecision d) {
		return new IllegalArgumentException("Unsupported structured query: " + d.entityType() + " / " + d.operation());
	}
}
