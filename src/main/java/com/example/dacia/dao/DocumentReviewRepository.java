package com.example.dacia.dao;

import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.DocumentReview;
import com.example.dacia.model.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentReviewRepository extends JpaRepository<DocumentReview, Long> {

    // Find reviews by status (APPROVED/REJECTED)
    List<DocumentReview> findByStatus(ReviewStatus status);

    // Find reviews for a specific document
    List<DocumentReview> findByDocument(Document document);
}