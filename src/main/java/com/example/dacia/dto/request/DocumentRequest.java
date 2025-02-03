package com.example.dacia.dto.request;

import com.example.dacia.model.enums.DocType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {
    private String title;
    private String content;
    private DocType type;
}
