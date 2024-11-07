package com.to_do_list.cqrs.user.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.user.command.LoginCommand;
import com.to_do_list.cqrs.user.dto.LoginDto;
import com.to_do_list.cqrs.user.dto.LoginResponse;
import com.to_do_list.entity.AppUser;
import com.to_do_list.jwt.JwtService;
import com.to_do_list.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginCommandHandler implements CommandHandler<LoginCommand, LoginResponse> {

    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    @Autowired
    public LoginCommandHandler(AuthenticationManager authenticationManager, AppUserRepository appUserRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse handle(LoginCommand command) {
        LoginDto loginDto = command.getLoginDto();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        AppUser appUser = appUserRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String jwtToken = jwtService.generateToken(appUser);
        return new LoginResponse(jwtToken,jwtService.getExpirationTime());
    }
}
