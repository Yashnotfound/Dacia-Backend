package com.example.dacia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;
    private Map<String, String> errors;
}

