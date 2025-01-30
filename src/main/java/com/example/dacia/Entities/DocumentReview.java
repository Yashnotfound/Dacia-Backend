package com.example.dacia.Entities;

import java.time.LocalDateTime;

public class DocumentReview {
    private Long id;
    private Long docId; // FK to Document
    private Long adminId; // FK to User (Admin)
    private ReviewStatus status;
    private String comments;
    private LocalDateTime lastReviewedAt;

}
enum ReviewStatus {
    APPROVED, REJECTED
}
