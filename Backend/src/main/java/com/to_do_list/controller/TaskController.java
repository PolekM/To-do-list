package com.to_do_list.controller;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.task.command.CreateTaskCommand;
import com.to_do_list.cqrs.task.command.DeleteTaskCommand;
import com.to_do_list.cqrs.task.command.UpdateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskDto;
import com.to_do_list.cqrs.task.dto.CreateTaskResponse;
import com.to_do_list.cqrs.task.dto.UpdateTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/task")
public class TaskController {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @Autowired
    public TaskController(CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }

    @PostMapping("/add")
    public CreateTaskResponse addNewTask(@RequestBody CreateTaskDto createTaskDto){
        CreateTaskCommand createTaskCommand = new CreateTaskCommand(createTaskDto);
        return commandDispatcher.dispatch(createTaskCommand);
    }

    @PutMapping("/update/{id}")
    public CreateTaskResponse updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDto updateTaskDto){
        System.out.println(updateTaskDto.getIsCompleted());
        UpdateTaskCommand updateTaskCommand = new UpdateTaskCommand(id,updateTaskDto);
        return commandDispatcher.dispatch(updateTaskCommand);
    }

    @DeleteMapping("/delete/{id}")
    public Void deleteTask(@PathVariable Integer id){
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(id);
        return commandDispatcher.dispatch(deleteTaskCommand);
    }
}
