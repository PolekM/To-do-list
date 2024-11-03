package com.to_do_list.service;

import com.to_do_list.Dto.LoginDto;
import com.to_do_list.Dto.LoginResponse;
import com.to_do_list.Dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<String> register(RegisterDto registerDto);

    LoginResponse login(LoginDto loginDto);
}
