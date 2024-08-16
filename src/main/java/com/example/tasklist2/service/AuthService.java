package com.example.tasklist2.service;

import com.example.tasklist2.web.dto.auth.JwtRequest;
import com.example.tasklist2.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
