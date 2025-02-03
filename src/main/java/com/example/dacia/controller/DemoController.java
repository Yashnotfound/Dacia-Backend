package com.example.dacia.controller;

import com.example.dacia.dto.response.DemoResponse;
import com.example.dacia.service.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/private")
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }
    @GetMapping("/UserDetails")
    public ResponseEntity<DemoResponse> userDetails(Principal principal) {
       return ResponseEntity.ok(demoService.getDemo(principal));
    }
}
