package com.example.dacia.Entities;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Long docId; // FK to Document
    private Long userId; // FK to User
    private String content;
    private LocalDateTime createdAt;
    private boolean isDeleted;
}
