package com.example.rally.model;

import java.util.List;

public record VectorRecord(String id, String type, String model, String embeddedText, List<Double> embedding) {
}
