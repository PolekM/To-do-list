package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListResponse;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.CreateListIllegalArgumentException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateListCommandHandler implements CommandHandler<CreateListCommand, CreateListResponse> {

    private final ToDoListRepository toDoListRepository;
    private final AppUserService appUserService;

    @Autowired
    public CreateListCommandHandler(ToDoListRepository toDoListRepository, AppUserService appUserService) {
        this.toDoListRepository = toDoListRepository;
        this.appUserService = appUserService;
    }

    @Transactional
    @Override
    public CreateListResponse handle(CreateListCommand command) {
        if (command.getCreateListDto().getName() == null) {
            throw new CreateListIllegalArgumentException("Name cannot be null");
        }

        AppUser user = appUserService.getCurrentUser();

        ToDoList newList = new ToDoList(command, user);
        ToDoList savedList = toDoListRepository.save(newList);
        return new CreateListResponse(savedList);
    }
}
