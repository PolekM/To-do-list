package com.to_do_list.controller;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.cqrs.to_do_list.dto.ToDoListCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/list")
@RestController
public class ToDoListController {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @Autowired
    public ToDoListController(CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }

    @PostMapping("/create")
    public ToDoListCreateResponse createList(@RequestBody CreateListDto createListDto) {
        CreateListCommand command = new CreateListCommand(createListDto);
        return commandDispatcher.dispatch(command);

    }


}
