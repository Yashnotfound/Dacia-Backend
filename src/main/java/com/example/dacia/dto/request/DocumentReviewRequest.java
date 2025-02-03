package com.example.dacia.dto.request;

import com.example.dacia.model.enums.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReviewRequest {
    @NotNull(message = "status must be provided")
    private ReviewStatus status;
    private String comment;
}
