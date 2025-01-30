package com.example.dacia.model.entities;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;

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
