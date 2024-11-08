package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.cqrs.to_do_list.dto.ToDoListCreateResponse;
import com.to_do_list.exception.List.CreateListIllegalArgumentException;
import com.to_do_list.repository.AppUserRepository;
import com.to_do_list.repository.ToDoListRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateListCommandHandler implements CommandHandler<CreateListCommand, ToDoListCreateResponse> {

    private final ToDoListRepository toDoListRepository;
    private final AppUserRepository appUserRepository;

    public CreateListCommandHandler(ToDoListRepository toDoListRepository, AppUserRepository appUserRepository) {
        this.toDoListRepository = toDoListRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    @Override
    public ToDoListCreateResponse handle(CreateListCommand command) {
        if(command.getCreateListDto().getName()==null){
            throw new CreateListIllegalArgumentException("Name cannot be null");
        }
        AppUser user = appUserRepository
                .findByEmail(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ToDoList newList = new ToDoList(command, user);
        ToDoList savedList = toDoListRepository.save(newList);
        return new ToDoListCreateResponse(savedList);
    }
}
