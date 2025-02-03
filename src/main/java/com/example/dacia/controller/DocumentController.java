package com.example.dacia.controller;

import com.example.dacia.dto.request.DocumentRequest;
import com.example.dacia.dto.request.DocumentReviewRequest;
import com.example.dacia.dto.response.DocumentResponse;
import com.example.dacia.dto.response.DocumentUpdateResponse;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
import com.example.dacia.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<String> createDocument(@RequestBody DocumentRequest document, Principal principal) {
        return ResponseEntity.ok(documentService.save(document, principal));
    }

    @GetMapping("/find")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) DocType type, @RequestParam(required = false) DocumentStatus status) {
        return ResponseEntity.ok(documentService.findDocuments(title, author, type, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDocument(Principal principal, @PathVariable Long id, @RequestBody(required = false) DocumentRequest document) {
        return ResponseEntity.ok(documentService.updateDocumentById(id, document, principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(documentService.deleteDocumentById(id, principal));
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<DocumentUpdateResponse> reviewDocument(@PathVariable Long id, @RequestBody DocumentReviewRequest request, Principal principal) {
        return ResponseEntity.ok(documentService.updateDocumentStatus(id, request, principal));
    }

}
