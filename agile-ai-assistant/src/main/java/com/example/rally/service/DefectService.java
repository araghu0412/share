package com.example.rally.service;

import org.springframework.stereotype.Service;
import com.example.rally.dto.DefectRequest;
import com.example.rally.model.*;
import com.example.rally.repository.*;

@Service
public class DefectService {
	private final DefectRepository repo;
	private final VectorRepository vectors;
	private final EmbeddingService embeddings;

	public DefectService(DefectRepository repo, VectorRepository vectors, EmbeddingService embeddings) {
		this.repo = repo;
		this.vectors = vectors;
		this.embeddings = embeddings;
	}

	public void save(DefectRequest r) {
		Defect d = new Defect(r.defectNumber(), r.userStoryNumber(), r.defectName(), r.defectDescription(),
				r.startDate(), r.endDate(), r.defectPoints(), r.release(), r.defectProgress());
		String text = "Defect Name: " + nz(r.defectName()) + "\nDefect Description: " + nz(r.defectDescription());
		VectorRecord v = new VectorRecord(r.defectNumber(), "DEFECT", embeddings.modelName(), text,
				embeddings.generate(text));
		repo.save(d);
		vectors.save(v);
	}

	private String nz(String s) {
		return s == null ? "" : s;
	}
}
