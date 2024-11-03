package com.to_do_list.service.imp;

import com.to_do_list.Dto.LoginDto;
import com.to_do_list.Dto.LoginResponse;
import com.to_do_list.Dto.RegisterDto;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.repository.AppRoleRepository;
import com.to_do_list.repository.AppUserRepository;
import com.to_do_list.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppRoleRepository appRoleRepository;

    public AuthenticationServiceImp(AppUserRepository appUserRepository,
                                    JwtService jwtService,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager,
                                    AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public ResponseEntity<String> register(RegisterDto registerDto) {
        AppUser appUser = new AppUser(
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                appRoleRepository.findByRoleName("ROLE_USER"));
        appUserRepository.save(appUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        AppUser appUser = appUserRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(appUser);
        return new LoginResponse(jwtToken,jwtService.getExpirationTime());
    }
}
