package com.example.dacia.controller;

import com.example.dacia.dto.response.DemoResponse;
import com.example.dacia.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/private")
@Tag(name = "Demo Controller", description = "Endpoints for demonstrating Swagger integration") // Tag for grouping APIs in Swagger UI
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/hello")
    @Operation(
            summary = "Hello Endpoint",
            description = "Returns a simple 'Hello World' message",
            security = @SecurityRequirement(name = "bearerAuth") // Specify security scheme if applicable
    )
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/UserDetails")
    @Operation(
            summary = "Get User Details",
            description = "Fetches user details based on the authenticated user's principal",
            security = @SecurityRequirement(name = "bearerAuth") // Specify security scheme if applicable
    )
    public ResponseEntity<DemoResponse> userDetails(Principal principal) {
        return ResponseEntity.ok(demoService.getDemo(principal));
    }
}
