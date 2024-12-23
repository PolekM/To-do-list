package com.to_do_list.configuration;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.task.command.CreateTaskCommand;
import com.to_do_list.cqrs.task.command.DeleteTaskCommand;
import com.to_do_list.cqrs.task.command.UpdateTaskCommand;
import com.to_do_list.cqrs.task.command.handler.CreateTaskCommandHandler;
import com.to_do_list.cqrs.task.command.handler.DeleteTaskCommandHandler;
import com.to_do_list.cqrs.task.command.handler.UpdateTaskCommandHandler;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.command.DeleteListCommand;
import com.to_do_list.cqrs.to_do_list.command.UpdateListCommand;
import com.to_do_list.cqrs.to_do_list.command.handler.CreateListCommandHandler;
import com.to_do_list.cqrs.to_do_list.command.handler.DeleteListCommandHandler;
import com.to_do_list.cqrs.to_do_list.command.handler.UpdateListCommandHandler;
import com.to_do_list.cqrs.to_do_list.query.GetListByIdQuery;
import com.to_do_list.cqrs.to_do_list.query.GetListQuery;
import com.to_do_list.cqrs.to_do_list.query.handler.GetListByIdQueryHandler;
import com.to_do_list.cqrs.to_do_list.query.handler.GetListQueryHandler;
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
    private final CreateListCommandHandler createListCommandHandler;
    private final GetListQueryHandler getListQueryHandler;
    private final GetListByIdQueryHandler getListByIdQueryHandler;
    private final CreateTaskCommandHandler createTaskCommandHandler;
    private final UpdateTaskCommandHandler updateTaskCommandHandler;
    private final DeleteTaskCommandHandler deleteTaskCommandHandler;
    private final DeleteListCommandHandler deleteListCommandHandler;
    private final UpdateListCommandHandler updateListCommandHandler;

    @Autowired
    public CqrsConfig(CommandDispatcher commandDispatcher,
                      QueryDispatcher queryDispatcher,
                      LoginCommandHandler loginCommandHandler,
                      RegisterCommandHandler registerCommandHandler,
                      CreateListCommandHandler createListCommandHandler,
                      GetListQueryHandler getListQueryHandler,
                      GetListByIdQueryHandler getListByIdQueryHandler,
                      CreateTaskCommandHandler createTaskCommandHandler,
                      UpdateTaskCommandHandler updateTaskCommandHandler,
                      DeleteTaskCommandHandler deleteTaskCommandHandler,
                      DeleteListCommandHandler deleteListCommandHandler,
                      UpdateListCommandHandler updateListCommandHandler) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
        this.loginCommandHandler = loginCommandHandler;
        this.registerCommandHandler = registerCommandHandler;
        this.createListCommandHandler = createListCommandHandler;
        this.getListQueryHandler = getListQueryHandler;
        this.getListByIdQueryHandler = getListByIdQueryHandler;
        this.createTaskCommandHandler = createTaskCommandHandler;
        this.updateTaskCommandHandler = updateTaskCommandHandler;
        this.deleteTaskCommandHandler = deleteTaskCommandHandler;
        this.deleteListCommandHandler = deleteListCommandHandler;
        this.updateListCommandHandler = updateListCommandHandler;
    }

    @PostConstruct
    public void registerHandlers(){
        commandDispatcher.registerHandler(LoginCommand.class, loginCommandHandler);
        commandDispatcher.registerHandler(RegisterCommand.class, registerCommandHandler);
        commandDispatcher.registerHandler(CreateListCommand.class, createListCommandHandler);
        queryDispatcher.registerHandler(GetListQuery.class,getListQueryHandler);
        queryDispatcher.registerHandler(GetListByIdQuery.class,getListByIdQueryHandler);
        commandDispatcher.registerHandler(CreateTaskCommand.class, createTaskCommandHandler);
        commandDispatcher.registerHandler(UpdateTaskCommand.class, updateTaskCommandHandler);
        commandDispatcher.registerHandler(DeleteTaskCommand.class, deleteTaskCommandHandler);
        commandDispatcher.registerHandler(DeleteListCommand.class, deleteListCommandHandler);
        commandDispatcher.registerHandler(UpdateListCommand.class, updateListCommandHandler);
    }

}
