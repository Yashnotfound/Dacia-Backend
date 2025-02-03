package com.example.dacia.service;

import com.example.dacia.dao.CommentRepository;
import com.example.dacia.dao.DocumentRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.CommentRequest;
import com.example.dacia.dto.response.CommentResponse;
import com.example.dacia.dto.response.CommentUpdateResponse;
import com.example.dacia.model.entities.Comment;
import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public CommentUpdateResponse save(long docId, CommentRequest request, Principal principal) {
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
            return CommentUpdateResponse.builder().message("Comment Successfully Added!!!").build();
    }
    public List<CommentResponse> showAllComments(long docId) {
        Document doc = documentRepository.findByIdAndDeleted(docId,false).orElseThrow(()->new RuntimeException("Document not found"));
        List<Comment> comments = commentRepository.findByDocument(doc);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for(Comment comment : comments) {
            commentResponses.add(CommentResponse.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .author(comment.getUser().getName())
                            .build());
        }
        return commentResponses;
    }
    @Transactional
    public CommentUpdateResponse deleteComment(long docId, long commentId,Principal principal) {
        Document doc = documentRepository.findById(docId).orElseThrow(()->new RuntimeException("Document not found"));
        if(doc.isDeleted()) {
            throw new RuntimeException("Document is deleted");
        }
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(()->new RuntimeException("User not found"));
        Comment comment = commentRepository.findByIdAndDeleted(commentId,false).orElseThrow(()->new RuntimeException("Comment not found"));
        if(!comment.getDocument().equals(doc)) {
            throw new RuntimeException("Document Id does not match");
        }
        if(comment.getUser().equals(user) || user.getRole().equals(Role.ADMIN) ) {
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
        else{
            throw new RuntimeException("User not authorized");
        }
        return CommentUpdateResponse.builder().message("Comment Deleted Successfully!!!").build();
    }
}
