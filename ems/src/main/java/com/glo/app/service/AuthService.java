package com.glo.app.service;

import com.glo.app.payload.LoginDto;
import com.glo.app.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
