package com.example.rally.repository;

import java.util.List;
import java.util.Optional;
import com.example.rally.model.Defect;

public interface DefectRepository {
	void save(Defect defect);

	List<Defect> findAll();

	Optional<Defect> findById(String defectNumber);
}
