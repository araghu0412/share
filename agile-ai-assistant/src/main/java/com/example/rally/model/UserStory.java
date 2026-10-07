package com.example.rally.model;

public record UserStory(String userStoryNumber, String featureNumber, String userStoryName, String userStoryDescription,
		String startDate, String endDate, Integer storyPoints, String release, String userStoryProgress) {
}
