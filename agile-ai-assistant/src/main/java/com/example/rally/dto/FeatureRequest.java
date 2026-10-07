package com.example.rally.dto;

public record FeatureRequest(String featureNumber, String epicNumber, String featureHeading, String featureDescription,
		String startDate, String endDate, String featureSize, String release) {
}
