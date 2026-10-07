package com.example.rally.repository;

import java.util.List;
import java.util.Optional;

import com.example.rally.model.Feature;

public interface FeatureRepository {

	void save(Feature feature);

	List<Feature> findAll();

	Optional<Feature> findById(String featureNumber);
}