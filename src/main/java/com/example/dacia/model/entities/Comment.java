package com.example.dacia.model.entities;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Document document;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private boolean isDeleted;
}