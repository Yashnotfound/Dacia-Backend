package com.example.dacia.dto.response;

import com.example.dacia.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoResponse {
    private Long id;
    private String name;
    private Role role;
    private String email;
    private LocalDateTime createdAt;
}
