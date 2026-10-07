package com.example.rally.exception;

import java.time.OffsetDateTime;

public record ApiErrorResponse(String code, String message, OffsetDateTime timestamp) {
}
