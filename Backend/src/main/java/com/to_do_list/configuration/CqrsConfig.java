package com.to_do_list.configuration;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.user.command.LoginCommand;
import com.to_do_list.cqrs.user.command.RegisterCommand;
import com.to_do_list.cqrs.user.command.handler.LoginCommandHandler;
import com.to_do_list.cqrs.user.command.handler.RegisterCommandHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CqrsConfig {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;
    private final LoginCommandHandler loginCommandHandler;
    private final RegisterCommandHandler registerCommandHandler;

    @Autowired
    public CqrsConfig(CommandDispatcher commandDispatcher,
                      QueryDispatcher queryDispatcher,
                      LoginCommandHandler loginCommandHandler,
                      RegisterCommandHandler registerCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
        this.loginCommandHandler = loginCommandHandler;
        this.registerCommandHandler = registerCommandHandler;
    }

    @PostConstruct
    public void registerHandlers(){
        commandDispatcher.registerHandler(LoginCommand.class, loginCommandHandler);
        commandDispatcher.registerHandler(RegisterCommand.class, registerCommandHandler);
    }
}
