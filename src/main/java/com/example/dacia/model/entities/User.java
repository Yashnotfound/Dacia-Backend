package com.example.dacia.model.entities;

import com.example.dacia.model.enums.Role;

public class User {
    private Long id;
    private String name;
    private String email;
    private String hashPassword;
    private Role type;
}