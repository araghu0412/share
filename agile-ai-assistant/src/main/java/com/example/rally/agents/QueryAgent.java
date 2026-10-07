package com.example.rally.agents;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.example.rally.agents.model.RouteDecision;
import com.example.rally.exception.QueryNotSupportedException;

@Component
public class QueryAgent {

	private final ChatClient chatClient;
	private final String systemPrompt;

	public QueryAgent(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.build();
		this.systemPrompt = loadSystemPrompt();
	}

	public RouteDecision route(String question) {

		try {
			RouteDecision decision = chatClient.prompt().system(systemPrompt).user(question).call()
					.entity(RouteDecision.class, spec -> spec.validateSchema());

			if (decision == null || decision.intent() == null) {
				throw new QueryNotSupportedException("We cannot provide an answer for this question.");
			}

			return decision;

		} catch (QueryNotSupportedException e) {
			throw e;
		} catch (Exception e) {
			throw new QueryNotSupportedException("We cannot provide an answer for this question.", e);
		}
	}

	private String loadSystemPrompt() {

		try {
			ClassPathResource resource = new ClassPathResource("agents/query-agent.md");

			return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

		} catch (IOException e) {
			throw new IllegalStateException("Unable to load query-agent.md", e);
		}
	}
}
