package com.example.dacia.Entities;
import java.time.LocalDateTime;

public class Document {
    private Long id;
    private String title;
    private String content;
    private DocumentStatus status;
    private Long createdBy; // FK to User
    private LocalDateTime createdAt;
    private DocType documentType;
    private LocalDateTime lastUpdated;
    private Long updatedBy; // FK to User
    private boolean isDeleted;
}
enum DocumentStatus {
    APPROVED, PENDING, REJECTED
}

enum DocType {
    GENERAL, API_CONTRACT
}
