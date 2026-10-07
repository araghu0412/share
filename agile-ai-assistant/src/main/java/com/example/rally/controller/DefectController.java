package com.example.rally.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.rally.dto.DefectRequest;
import com.example.rally.service.DefectService;

@RestController
@RequestMapping("/api/defects")
public class DefectController {
	private final DefectService service;

	public DefectController(DefectService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody List<DefectRequest> requests) {
		requests.forEach(service::save);
		return ResponseEntity.ok(requests.size() + " defect(s) metadata and semantic embeddings saved");
	}
}
