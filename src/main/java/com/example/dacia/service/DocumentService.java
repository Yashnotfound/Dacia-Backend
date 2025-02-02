package com.example.dacia.service;

import com.example.dacia.dao.DocumentRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.DocumentCreateRequest;
import com.example.dacia.dto.response.DocumentViewResponse;
import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public String save(DocumentCreateRequest request, Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        var document = Document.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .documentType(request.getType())
                .createdBy(user.orElse(null))
                .updatedBy(user.orElse(null))
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .status(DocumentStatus.PENDING)
                .build();
        documentRepository.save(document);
        return "Document saved";
    }
    public List<DocumentViewResponse> findDocuments(String title, String author, DocType docType,DocumentStatus docStatus) {

        List<Document> docs = documentRepository.searchAndFilter(title,author, docType, docStatus);
        List<DocumentViewResponse> responses = new ArrayList<>();
        for (Document document : docs) {
            responses.add(DocumentViewResponse.builder()
                    .id(document.getId())
                    .title(document.getTitle())
                    .content(document.getContent())
                    .type(document.getDocumentType())
                    .createdBy(document.getCreatedBy().getName())
                    .createdDate(document.getCreatedAt())
                    .lastModifiedBy(document.getUpdatedBy().getName())
                    .lastModifiedDate(document.getLastUpdated())
                    .build());
        }
        return responses;
    }

    public DocumentViewResponse getDocumentById(Long id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
            return DocumentViewResponse.builder()
                    .id(document.getId())
                    .title(document.getTitle())
                    .content(document.getContent())
                    .type(document.getDocumentType())
                    .createdBy(document.getCreatedBy().getName())
                    .createdDate(document.getCreatedAt())
                    .lastModifiedBy(document.getUpdatedBy().getName())
                    .lastModifiedDate(document.getLastUpdated())
                    .build();
    }

}
