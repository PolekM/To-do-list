package com.to_do_list.controller;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.user.command.LoginCommand;
import com.to_do_list.cqrs.user.command.RegisterCommand;
import com.to_do_list.cqrs.user.dto.LoginDto;
import com.to_do_list.cqrs.user.dto.LoginResponse;
import com.to_do_list.cqrs.user.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(("/auth"))
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @Autowired
    public AuthenticationController(CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }



    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto){
        RegisterCommand registerCommand = new RegisterCommand(registerDto);
        return commandDispatcher.dispatch(registerCommand);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDto loginDto){
       LoginCommand loginCommand = new LoginCommand(loginDto);
       return commandDispatcher.dispatch(loginCommand);
    }
}
