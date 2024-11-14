package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.task.command.UpdateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskResponse;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTaskCommandHandler implements CommandHandler<UpdateTaskCommand, CreateTaskResponse> {

    private final TaskRepository taskRepository;
    private final AppUserService appUserService;
    private final ToDoListRepository toDoListRepository;

    @Autowired
    public UpdateTaskCommandHandler(TaskRepository taskRepository, AppUserService appUserService,ToDoListRepository toDoListRepository) {
        this.taskRepository = taskRepository;
        this.appUserService = appUserService;
        this.toDoListRepository = toDoListRepository;
    }

    @Transactional
    @Override
    public CreateTaskResponse handle(UpdateTaskCommand command) {
        Task taskToUpdate = taskRepository.findById(command.getId()).orElseThrow(() -> new TaskNotFoundException("Task Not Found"));
        ToDoList userList = toDoListRepository.findById(taskToUpdate.getList().getId()).orElseThrow(() -> new ToDoListNotFoundException("List Not Found"));
        AppUser appUser = appUserService.getCurrentUser();
        if(!userList.getAppUser().equals(appUser)) {
            throw new WrongCredentialException("You cannot update not your task");
        }

        if (command.getUpdateTask().getIsCompleted() != null)
            taskToUpdate.setIsCompleted(command.getUpdateTask().getIsCompleted());
        if (command.getUpdateTask().getDescription() != null)
            taskToUpdate.setDescription(command.getUpdateTask().getDescription());
        Task updatedTask = taskRepository.save(taskToUpdate);
        return new CreateTaskResponse(updatedTask);
    }

}
