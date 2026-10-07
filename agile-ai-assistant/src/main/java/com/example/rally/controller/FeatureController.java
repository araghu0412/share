package com.example.rally.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rally.dto.FeatureRequest;
import com.example.rally.service.FeatureService;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
	private final FeatureService service;

	public FeatureController(FeatureService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody List<FeatureRequest> requests) {
		requests.forEach(service::save);
		return ResponseEntity.ok(requests.size() + " feature(s) metadata and semantic embeddings saved");
	}
}
