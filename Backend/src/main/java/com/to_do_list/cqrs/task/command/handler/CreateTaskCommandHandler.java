package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.task.command.CreateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskResponse;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.TaskRepository;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTaskCommandHandler implements CommandHandler<CreateTaskCommand, CreateTaskResponse> {

    private final TaskRepository taskRepository;
    private final AppUserService appUserService;
    private final ToDoListRepository toDoListRepository;

    @Autowired
    public CreateTaskCommandHandler(TaskRepository taskRepository, AppUserService appUserService, ToDoListRepository toDoListRepository) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
        this.toDoListRepository = toDoListRepository;
    }

    @Transactional
    @Override
    public CreateTaskResponse handle(CreateTaskCommand command) {
        ToDoList toDoList = toDoListRepository.findById(command.getCreateTaskDto().getListId()).orElseThrow(() -> new ToDoListNotFoundException("List Not Found"));
        AppUser currentUser = appUserService.getCurrentUser();
        if (!toDoList.getAppUser().equals(currentUser)) {
            throw new WrongCredentialException("You cannot add task to another user`s list");
        }
        Task savedTask = taskRepository.save(new Task(command.getCreateTaskDto().getDescription(),toDoList));
        return new CreateTaskResponse(savedTask);
    }
}
