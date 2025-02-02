package com.example.dacia.dao;

import com.example.dacia.model.entities.Document;
import com.example.dacia.model.entities.User;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Find documents by status (e.g., PENDING)
    List<Document> findByStatus(DocumentStatus status);

    // Find documents by type (e.g., API_CONTRACT)
    List<Document> findByDocumentType(DocType documentType);

    // Find documents created by a specific user
    List<Document> findByCreatedBy(User createdBy);
    List<Document> findByIsDeletedFalse();
    @Query("SELECT d FROM Document d WHERE " +
            "(:author IS NULL OR d.createdBy.name = :author) AND " +
            "(:docType IS NULL OR d.documentType = :docType) AND " +
            "(:documentStatus IS NULL OR d.status = :documentStatus) AND "+
            "d.isDeleted = false")
    List<Document> findByFilters(@Param("author") String author,
                                 @Param("docType") DocType docType,@Param("documentStatus") DocumentStatus documentStatus);
}