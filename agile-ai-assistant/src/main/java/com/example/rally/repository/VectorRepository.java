package com.example.rally.repository;

import java.util.List;

import com.example.rally.model.VectorRecord;

public interface VectorRepository {

	void save(VectorRecord vectorRecord);

	List<VectorRecord> findAll();
}