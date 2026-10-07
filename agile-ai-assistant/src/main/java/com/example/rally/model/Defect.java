package com.example.rally.model;

public record Defect(String defectNumber, String userStoryNumber, String defectName, String defectDescription,
		String startDate, String endDate, Integer defectPoints, String release, String defectProgress) {
}
