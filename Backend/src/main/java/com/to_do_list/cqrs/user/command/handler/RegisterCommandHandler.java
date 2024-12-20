package com.to_do_list.cqrs.user.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.user.command.RegisterCommand;
import com.to_do_list.cqrs.user.dto.RegisterResponse;
import com.to_do_list.entity.AppUser;
import com.to_do_list.exception.user.EmailIsBusyException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.AppRoleRepository;
import com.to_do_list.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterCommandHandler implements CommandHandler<RegisterCommand,RegisterResponse> {

    private final PasswordEncoder passwordEncoder;
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public RegisterCommandHandler(PasswordEncoder passwordEncoder, AppRoleRepository appRoleRepository, AppUserRepository appUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public RegisterResponse handle(RegisterCommand command) {

        if(appUserRepository.findByEmail(command.getRegisterDto().getEmail()).isPresent()){
            throw new EmailIsBusyException("Email is busy");
        }

        if(!command.getRegisterDto().getPassword().equals(command.getRegisterDto().getRepeatPassword())){
            throw new WrongCredentialException("password does not match");
        }
        AppUser appUser = new AppUser(
                command.getRegisterDto().getEmail(),
                passwordEncoder.encode(command.getRegisterDto().getPassword()),
                appRoleRepository.findByRoleName("ROLE_USER"));
        appUserRepository.save(appUser);
        return new RegisterResponse("User registered successfully");
    }
}
