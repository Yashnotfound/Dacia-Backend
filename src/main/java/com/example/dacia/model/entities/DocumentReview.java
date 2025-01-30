package com.example.dacia.model.entities;

import com.example.dacia.model.enums.ReviewStatus;

import java.time.LocalDateTime;

public class DocumentReview {
    private Long id;
    private Long docId; // FK to Document
    private Long adminId; // FK to User (Admin)
    private ReviewStatus status;
    private String comments;
    private LocalDateTime lastReviewedAt;

}