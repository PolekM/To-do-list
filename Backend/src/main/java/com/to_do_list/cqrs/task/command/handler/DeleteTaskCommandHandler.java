package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.task.command.DeleteTaskCommand;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.Task.TaskNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.TaskRepository;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskCommandHandler implements CommandHandler<DeleteTaskCommand,Void> {

    private final AppUserService appUserService;
    private final TaskRepository taskRepository;
    private final ToDoListRepository toDoListRepository;

    @Autowired
    public DeleteTaskCommandHandler(AppUserService appUserService, TaskRepository taskRepository,ToDoListRepository toDoListRepository) {
        this.appUserService = appUserService;
        this.taskRepository = taskRepository;
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public Void handle(DeleteTaskCommand command) {
        Task task = taskRepository.findById(command.getId()).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        ToDoList toDoList = toDoListRepository.findById(task.getList().getId()).orElseThrow(() -> new ToDoListNotFoundException("List Not Found"));
        AppUser appUser = appUserService.getCurrentUser();
        if (!toDoList.getAppUser().equals(appUser)) {
            throw new WrongCredentialException("You cannot delete not your task");
        }
        taskRepository.delete(task);
        return null;
    }
}
