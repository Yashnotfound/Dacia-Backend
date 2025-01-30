package com.example.dacia.Entities;
import org.hibernate.usertype.UserType;

public class User {
    private Long id;
    private String name;
    private String email;
    private String hashPassword;
    private Role type;
}

enum Role {
    ADMIN, END_USER
}