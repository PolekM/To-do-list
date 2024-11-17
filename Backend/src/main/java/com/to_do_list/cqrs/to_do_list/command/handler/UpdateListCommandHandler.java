package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.to_do_list.command.UpdateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListResponse;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateListCommandHandler implements CommandHandler<UpdateListCommand, CreateListResponse> {

    private final ToDoListRepository toDoListRepository;
    private final AppUserService appUserService;

    @Autowired
    public UpdateListCommandHandler(ToDoListRepository toDoListRepository, AppUserService appUserService) {
        this.toDoListRepository = toDoListRepository;
        this.appUserService = appUserService;
    }

    @Override
    public CreateListResponse handle(UpdateListCommand command) {
        ToDoList toDoList = toDoListRepository.findById(command.getId()).orElseThrow(() -> new ToDoListNotFoundException("List not Found"));
        AppUser currentUser = appUserService.getCurrentUser();
        if (!toDoList.getAppUser().equals(currentUser))
            throw new WrongCredentialException("you can't update a list that isn't yours");
        if (command.getUpdateListDto().getName() != null)
            toDoList.setName(command.getUpdateListDto().getName());

        return new CreateListResponse(toDoListRepository.save(toDoList));
    }
}
