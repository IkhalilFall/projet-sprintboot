package com.ipd.Gestion_Scolaire.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}