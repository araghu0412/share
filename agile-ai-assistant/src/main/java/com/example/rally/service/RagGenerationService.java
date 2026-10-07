package com.example.rally.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.example.rally.model.Defect;
import com.example.rally.model.DefectSearchResult;
import com.example.rally.model.Feature;
import com.example.rally.model.FeatureSearchResult;
import com.example.rally.model.UserStory;
import com.example.rally.model.UserStorySearchResult;

import tools.jackson.databind.json.JsonMapper;

@Service
public class RagGenerationService {

	private final ChatClient chat;
	private final JsonMapper json;
	private final String prompt;

	public RagGenerationService(ChatClient.Builder builder, JsonMapper json) {
		this.chat = builder.build();
		this.json = json;
		this.prompt = load();
	}

	public String generateFeatureAnswer(String question, List<FeatureSearchResult> results) {
		return generate(question, results.stream().map(FeatureSearchResult::feature).toList(), null);
	}

	public String generateUserStoryAnswer(String question, List<UserStorySearchResult> results) {
		List<UserStory> records = results.stream().map(UserStorySearchResult::userStory).toList();
		return generate(question, records, buildUserStoryComputedFacts(records));
	}

	public String generateDefectAnswer(String question, List<DefectSearchResult> results) {
		return generate(question, results.stream().map(DefectSearchResult::defect).toList(), null);
	}

	public String generateFeatureRecordsAnswer(String question, List<Feature> records) {
		return generate(question, records, null);
	}

	public String generateDefectRecordsAnswer(String question, List<Defect> records) {
		return generate(question, records, null);
	}

	public String generateUserStoryRecordsAnswer(String question, List<UserStory> records) {
		return generate(question, records, buildUserStoryComputedFacts(records));
	}

	public String generateStructuredAnswer(String question, Object result) {
		if (result instanceof List<?> list && !list.isEmpty() && list.get(0) instanceof UserStory) {
			@SuppressWarnings("unchecked")
			List<UserStory> records = (List<UserStory>) result;
			return generate(question, records, buildUserStoryComputedFacts(records));
		}
		return generate(question, result, null);
	}

	/**
	 * Story-point arithmetic is deliberately deterministic. The LLM receives these
	 * values as authoritative facts and is responsible only for wording and
	 * presentation.
	 */
	private String buildUserStoryComputedFacts(List<UserStory> records) {
		if (records == null || records.isEmpty()) {
			return null;
		}

		Map<String, Integer> storyPointsByRelease = records.stream().filter(Objects::nonNull)
				.collect(Collectors.groupingBy(story -> story.release() == null ? "UNKNOWN" : story.release(),
						LinkedHashMap::new,
						Collectors.summingInt(story -> story.storyPoints() == null ? 0 : story.storyPoints())));

		int totalStoryPoints = records.stream().filter(Objects::nonNull).map(UserStory::storyPoints)
				.filter(Objects::nonNull).mapToInt(Integer::intValue).sum();

		StringBuilder facts = new StringBuilder();
		facts.append("AUTHORITATIVE STORY POINT TOTALS (calculated by Java; DO NOT recalculate):\n");
		storyPointsByRelease.forEach(
				(release, total) -> facts.append("- Release ").append(release).append(": ").append(total).append("\n"));
		facts.append("- Overall total: ").append(totalStoryPoints);
		return facts.toString();
	}

	private String generate(String question, Object records, String computedFacts) {
		if (records == null || (records instanceof Collection<?> collection && collection.isEmpty())) {
			return "No relevant Rally records were found.";
		}

		try {
			String context = json.writerWithDefaultPrettyPrinter().writeValueAsString(records);
			String facts = computedFacts == null ? ""
					: "\n\nCOMPUTED FACTS (authoritative values calculated by the application):\n" + computedFacts;

			String response = chat.prompt().system(prompt)
					.user("USER QUESTION:\n" + question + "\n\nRALLY CONTEXT:\n" + context + facts).call().content();

			return cleanMarkdownResponse(response);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to generate answer", e);
		}
	}

	private String load() {
		try {
			return StreamUtils.copyToString(new ClassPathResource("agents/rag-generation.md").getInputStream(),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load rag-generation.md", e);
		}
	}

	private String cleanMarkdownResponse(String response) {

		if (response == null || response.isBlank()) {
			return response;
		}

		String cleaned = response.trim();

		if (cleaned.startsWith("```markdown")) {
			cleaned = cleaned.substring("```markdown".length()).trim();
		} else if (cleaned.startsWith("```")) {
			cleaned = cleaned.substring(3).trim();
		}

		if (cleaned.endsWith("```")) {
			cleaned = cleaned.substring(0, cleaned.length() - 3).trim();
		}

		return cleaned;
	}
}
