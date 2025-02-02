package com.example.dacia.controller;

import com.example.dacia.dto.request.DocumentCreateRequest;
import com.example.dacia.dto.response.DocumentViewResponse;
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
    public ResponseEntity<String> createDocument(@RequestBody DocumentCreateRequest document, Principal principal) {
        return ResponseEntity.ok(documentService.save(document, principal));
    }
    @GetMapping
    public ResponseEntity<List<DocumentViewResponse>> getAllDocuments() {
        return ResponseEntity.ok(documentService.view());
    }

}
