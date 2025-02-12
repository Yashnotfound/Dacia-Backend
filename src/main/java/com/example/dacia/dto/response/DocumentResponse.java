package com.example.dacia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentResponse {
    private long id;
    private String title;
    private String content;
    private String author;
    private String description;
    private LocalDateTime createdAt;
}
