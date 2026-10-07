package com.example.rally.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rally.service.QueryService;

@RestController
@RequestMapping("/api/query")
public class QueryController {

	private final QueryService queryService;

	public QueryController(QueryService queryService) {
		this.queryService = queryService;
	}

	@PostMapping
	public Object query(@RequestBody String question) {

		return queryService.query(question);
	}
}