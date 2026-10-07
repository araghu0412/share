package com.example.rally.dto;

public record UserStoryRequest(String userStoryNumber, String featureNumber, String userStoryName,
		String userStoryDescription, String startDate, String endDate, Integer storyPoints, String release,
		String userStoryProgress) {
}
