package com.example.dacia.controller;

import com.example.dacia.dto.request.CommentRequest;
import com.example.dacia.dto.response.CommentResponse;
import com.example.dacia.dto.response.CommentUpdateResponse;
import com.example.dacia.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documents/{docId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<CommentUpdateResponse> addComment(@PathVariable Long docId, @Valid @RequestBody CommentRequest comment, Principal principal) {
        return ResponseEntity.ok(commentService.save(docId,comment,principal));
    }
    @GetMapping()
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long docId) {
        return ResponseEntity.ok(commentService.showAllComments(docId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentUpdateResponse> deleteComment(@PathVariable Long docId,@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(commentService.deleteComment(docId,id,principal));
    }

}
