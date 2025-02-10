package com.example.dacia.dto.request;

import com.example.dacia.model.enums.DocType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    @NotNull(message = "title cannot be null")
    private String title;
    @NotNull(message = "content cannot be null")
    private String content;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Doctype must be provided")
    private DocType type;
}
