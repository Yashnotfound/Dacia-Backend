package com.example.dacia.dao;

import com.example.dacia.model.entities.DocumentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DocumentReviewRepository extends JpaRepository<DocumentReview, Long> {

}