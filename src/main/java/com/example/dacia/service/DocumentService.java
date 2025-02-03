package com.example.dacia.service;

import com.example.dacia.dao.DocumentRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.DocumentRequest;
import com.example.dacia.dto.response.DocumentResponse;
import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
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
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Transactional
    public String save(DocumentRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        if(request.getTitle().isEmpty() || request.getTitle().trim().isEmpty()){
            throw new RuntimeException("Title is empty");
        }
        if(documentRepository.existsByTitle(request.getTitle())){
            throw new RuntimeException("Title already exist");
        }
        var document = Document.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .documentType(request.getType())
                .createdBy(user)
                .updatedBy(user)
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .status(DocumentStatus.PENDING)
                .build();
        documentRepository.save(document);
        return "Document saved";
    }

    public List<DocumentResponse> findDocuments(String title, String author, DocType docType, DocumentStatus docStatus) {

        List<Document> docs = documentRepository.searchAndFilter(title, author, docType, docStatus);
        List<DocumentResponse> responses = new ArrayList<>();
        for (Document document : docs) {
            responses.add(DocumentResponse.builder()
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

    public DocumentResponse getDocumentById(Long id) {
        Document document = documentRepository.findByIdAndDeleted(id,false).orElseThrow(() -> new RuntimeException("Document not found"));
        return DocumentResponse.builder()
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
    @Transactional
    public String updateDocumentById(Long id, DocumentRequest request, DocumentStatus status, Principal principal) {
        Document document = documentRepository.findByIdAndDeleted(id,false)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isOwner = user.getName().equals(document.getCreatedBy().getName());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("User not authorized");
        }

        if (request != null) {
            document.setTitle(request.getTitle());
            document.setContent(request.getContent());
            document.setUpdatedBy(user);
            document.setLastUpdated(LocalDateTime.now());
            document.setStatus(DocumentStatus.PENDING);
        }

        if (isAdmin && status != null) {
            document.setStatus(status);
        } else if (status != null) {
            throw new RuntimeException("Only admins can change document status");
        } else {
            throw new RuntimeException("Status not found");
        }

        documentRepository.save(document);
        return "Document updated successfully";
    }
    @Transactional
    public String deleteDocumentById(Long id, Principal principal) {
        Document document = documentRepository.findByIdAndDeleted(id,false).orElseThrow(() -> new RuntimeException("Document not found"));
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getName().equals(document.getCreatedBy().getName()) || user.getRole() == Role.ADMIN) {
            document.setDeleted(true);
            document.setUpdatedBy(user);
            document.setLastUpdated(LocalDateTime.now());
            documentRepository.save(document);
            return "Document deleted";
        }
        throw new RuntimeException("User not authorized");
    }

}
