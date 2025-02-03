package com.example.dacia.dao;

import com.example.dacia.model.entities.Document;
import com.example.dacia.model.enums.DocType;
import com.example.dacia.model.enums.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE " +
            "(:title IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR LOWER(d.createdBy.name) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:type IS NULL OR d.documentType = :type) " +
            "AND (:status IS NULL OR d.status = :status) " +
            "AND d.deleted = false")
    List<Document> searchAndFilter(
            @Param("title") String title,
            @Param("author") String author,
            @Param("type") DocType type,
            @Param("status") DocumentStatus status);


    Optional<Document> findByIdAndDeleted(Long id, boolean deleted);
    boolean existsByTitle(String title);

}
