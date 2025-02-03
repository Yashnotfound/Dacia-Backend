package com.example.dacia.controller;

import com.example.dacia.dto.request.CommentRequest;
import com.example.dacia.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/documents/{docId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<String> addComment(@PathVariable Long docId, @RequestBody CommentRequest comment, Principal principal) {
        return ResponseEntity.ok(commentService.save(docId,comment,principal));
    }
    public ResponseEntity<String> deleteComment(@PathVariable Long docId, @PathVariable Long commentId, Principal principal) {}

}
