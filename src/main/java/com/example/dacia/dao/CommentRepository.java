package com.example.dacia.dao;

import com.example.dacia.model.entities.Comment;
import com.example.dacia.model.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Find comments for a specific document
    List<Comment> findByDocument(Document document);
}