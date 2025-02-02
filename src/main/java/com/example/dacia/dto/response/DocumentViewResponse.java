package com.example.dacia.dto.response;

import com.example.dacia.model.enums.DocType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentViewResponse {
    private long id;
    private String title;
    private String content;
    private String createdBy;
    private DocType type;
    private String lastModifiedBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
