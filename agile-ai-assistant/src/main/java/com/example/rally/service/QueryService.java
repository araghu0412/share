package com.example.rally.service;

import org.springframework.stereotype.Service;

import com.example.rally.agents.QueryAgent;
import com.example.rally.agents.model.OperationType;
import com.example.rally.agents.model.RouteDecision;
import com.example.rally.exception.QueryNotSupportedException;
import com.example.rally.handler.HybridQueryHandler;
import com.example.rally.handler.RagQueryHandler;
import com.example.rally.handler.StructuredQueryHandler;

@Service
public class QueryService {

	private final QueryAgent queryAgent;
	private final StructuredQueryHandler structuredQueryHandler;
	private final RagQueryHandler ragQueryHandler;
	private final HybridQueryHandler hybridQueryHandler;
	private final RagGenerationService generator;

	public QueryService(QueryAgent queryAgent, StructuredQueryHandler structuredQueryHandler,
			RagQueryHandler ragQueryHandler, HybridQueryHandler hybridQueryHandler, RagGenerationService generator) {

		this.queryAgent = queryAgent;
		this.structuredQueryHandler = structuredQueryHandler;
		this.ragQueryHandler = ragQueryHandler;
		this.hybridQueryHandler = hybridQueryHandler;
		this.generator = generator;
	}

	public Object query(String question) {

		RouteDecision decision = queryAgent.route(question);

		if (decision.intent() == null) {
			throw new QueryNotSupportedException("We cannot provide an answer for this question.");
		}

		return switch (decision.intent()) {

		case STRUCTURED -> handleStructuredQuery(question, decision);

		case RAG -> ragQueryHandler.handle(question, decision);

		case HYBRID -> hybridQueryHandler.handle(question, decision);

		case UNSUPPORTED -> throw new QueryNotSupportedException("We cannot provide an answer for this question.");
		};
	}

	/**
	 * Handles deterministic structured queries.
	 *
	 * COUNT operations are returned directly because Java has already calculated
	 * the authoritative numeric result.
	 *
	 * GET and LIST operations are passed to the response generator so the LLM can
	 * present the retrieved data in the format requested by the user, such as
	 * table, paragraph, bullet points, etc.
	 */
	private Object handleStructuredQuery(String question, RouteDecision decision) {

		Object result = structuredQueryHandler.handle(decision);

		/*
		 * COUNT is deterministic.
		 *
		 * Example:
		 *
		 * Question: How many features are there?
		 *
		 * StructuredQueryHandler returns: 20
		 *
		 * Return 20 directly.
		 *
		 * DO NOT send the value back through the LLM because the LLM must not
		 * recalculate or modify authoritative numeric results.
		 */
		if (decision.operation() == OperationType.COUNT) {
			return result;
		}

		/*
		 * GET and LIST contain retrieved Agile records.
		 *
		 * These can safely go through the generation layer because the LLM is only
		 * responsible for presenting the records in the user's requested format.
		 *
		 * Examples:
		 *
		 * - Markdown table - Paragraph - Bullet points - Concise natural-language
		 * response
		 */
		return generator.generateStructuredAnswer(question, result);
	}
}