package com.example.rally.agents.model;

import java.util.List;
import java.util.Map;

public record RouteDecision(IntentType intent, EntityType entityType, OperationType operation,
		Map<String, List<String>> filters, String searchText) {
}
