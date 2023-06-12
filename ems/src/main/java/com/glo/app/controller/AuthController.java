package com.glo.app.controller;

import com.glo.app.payload.LoginDto;
import com.glo.app.payload.RegisterDto;
import com.glo.app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    //Constructor based dependency injection
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Spring boot REST api for login
    @PostMapping(value = {"/sign-in","/login"})
    public ResponseEntity<String> login(
            @RequestBody LoginDto loginDto
    )
    {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    //Spring boot REST api for register
    @PostMapping(value = {"/sign-up","/register"})
    public ResponseEntity<String> register(
            @RequestBody RegisterDto registerDto
    )
    {
        return ResponseEntity.ok(authService.register(registerDto));
    }
}
