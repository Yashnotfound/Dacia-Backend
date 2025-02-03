package com.example.dacia.service;

import com.example.dacia.dao.CommentRepository;
import com.example.dacia.dao.DocumentRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.CommentRequest;
import com.example.dacia.model.entities.Comment;
import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public String save(long docId,CommentRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()->new RuntimeException("User not found"));
        Document doc = documentRepository.findById(docId).orElseThrow(()->new RuntimeException("Document not found"));
        if(doc.isDeleted()) {
            throw new RuntimeException("Document is deleted");
        }
            Comment comment = Comment.builder()
                            .content(request.getContent())
                            .user(user)
                            .document(doc)
                            .createdAt(LocalDateTime.now())
                            .build();
            commentRepository.save(comment);
            return "Comment saved successfully";
    }
}
