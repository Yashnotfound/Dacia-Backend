package com.example.dacia.service;

import com.example.dacia.dao.DocumentRepository;
import com.example.dacia.dao.DocumentReviewRepository;
import com.example.dacia.dao.UserRepository;
import com.example.dacia.dto.request.DocumentRequest;
import com.example.dacia.dto.request.DocumentReviewRequest;
import com.example.dacia.dto.response.DocumentResponse;
import com.example.dacia.dto.response.UpdateResponse;
import com.example.dacia.exceptionHandler.*;
import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.DocumentReview;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
import com.example.dacia.model.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentReviewRepository documentReviewRepository;

    @Transactional(rollbackOn = Exception.class)
    public UpdateResponse save(DocumentRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (request.getTitle().isEmpty() || request.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Title is empty");
        }

        if (documentRepository.existsByTitle(request.getTitle())) {
            throw new DuplicateDocumentException("Title already exists");
        }

        Document document = Document.builder()
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
        return UpdateResponse.builder().message("Document Saved Successfully").build();
    }

    public List<DocumentResponse> findDocuments(String title, String author, DocType docType, DocumentStatus docStatus) {
        List<Document> docs = documentRepository.searchAndFilter(title, author, docType, docStatus);
        return docs.stream().map(this::mapToDocumentResponse).collect(Collectors.toList());
    }

    public DocumentResponse getDocumentById(Long id) {
        Document document = documentRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));
        return mapToDocumentResponse(document);
    }

    @Transactional(rollbackOn = Exception.class)
    public UpdateResponse updateDocumentById(Long id, DocumentRequest request, Principal principal) {
        Document document = documentRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isOwner = user.getName().equals(document.getCreatedBy().getName());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new UnauthorizedException("User not authorized to update this document");
        }

        if (request != null) {
            document.setTitle(request.getTitle());
            document.setContent(request.getContent());
            document.setUpdatedBy(user);
            document.setLastUpdated(LocalDateTime.now());
            document.setStatus(DocumentStatus.PENDING);
        }
        if (isAdmin) {
            document.setStatus(DocumentStatus.APPROVED);
        }
        documentRepository.save(document);
        return UpdateResponse.builder().message("Document Updated Successfully").build();
    }

    @Transactional(rollbackOn = Exception.class)
    public UpdateResponse deleteDocumentById(Long id, Principal principal) {
        Document document = documentRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getName().equals(document.getCreatedBy().getName()) || user.getRole() == Role.ADMIN) {
            document.setDeleted(true);
            document.setUpdatedBy(user);
            document.setLastUpdated(LocalDateTime.now());
            documentRepository.save(document);
            return UpdateResponse.builder().message("Document Deleted Successfully").build();
        }
        throw new UnauthorizedException("User not authorized to delete this document");
    }

    @Transactional(rollbackOn = Exception.class)
    public UpdateResponse updateDocumentStatus(@PathVariable Long id, DocumentReviewRequest request, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("Only Admin can change status of a document");
        }

        Document doc = documentRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new DocumentNotFoundException("Document not found"));

        DocumentStatus documentStatus = DocumentStatus.valueOf(request.getStatus().name());
        if (documentStatus == doc.getStatus()) {
            throw new DocumentStatusException("Document status is already set to " + documentStatus);
        }

        DocumentReview review = DocumentReview.builder()
                .admin(user)
                .document(doc)
                .status(request.getStatus())
                .comments(request.getComment())
                .build();
        documentReviewRepository.save(review);

        doc.setStatus(documentStatus);
        documentRepository.save(doc);

        return UpdateResponse.builder().message("Document Status Changed by Admin!!!").build();
    }

    private DocumentResponse mapToDocumentResponse(Document document) {
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
}

