package com.exemplo.usuario.service;

import com.exemplo.usuario.dto.LoginRequest;
import com.exemplo.usuario.dto.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest request);
}
