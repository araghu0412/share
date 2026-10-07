package com.example.rally.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rally.dto.UserStoryRequest;
import com.example.rally.service.UserStoryService;

@RestController
@RequestMapping("/api/user-stories")
public class UserStoryController {
	private final UserStoryService service;

	public UserStoryController(UserStoryService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody List<UserStoryRequest> requests) {
		requests.forEach(service::save);
		return ResponseEntity.ok(requests.size() + " user story(s) metadata and semantic embeddings saved");
	}
}
