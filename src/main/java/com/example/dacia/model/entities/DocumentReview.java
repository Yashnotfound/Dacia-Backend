package com.example.dacia.model.entities;

import com.example.dacia.model.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Document document;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User admin;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String comments;

    @CreationTimestamp
    @Column()
    private LocalDateTime lastReviewedAt;
}