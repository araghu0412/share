package com.example.rally.dto;

public record DefectRequest(String defectNumber, String userStoryNumber, String defectName, String defectDescription,
		String startDate, String endDate, Integer defectPoints, String release, String defectProgress) {
}
