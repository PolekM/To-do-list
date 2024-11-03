package com.to_do_list.controller;

import com.to_do_list.Dto.LoginDto;
import com.to_do_list.Dto.LoginResponse;
import com.to_do_list.Dto.RegisterDto;
import com.to_do_list.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(("/auth"))
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return authenticationService.register(registerDto);
    }

    @GetMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
        return authenticationService.login(loginDto);
    }
}
