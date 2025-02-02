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
    private String Title;
    private String Content;
    private String CreatedBy;
    private DocType type;
    private String LastModifiedBy;
    private LocalDateTime CreatedDate;
    private LocalDateTime LastModifiedDate;
}
