package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.common.CommandHandler;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.repository.AppUserRepository;
import com.to_do_list.repository.ToDoListRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateListCommandHandler implements CommandHandler<CreateListCommand,String> {

    private final ToDoListRepository toDoListRepository;
    private final AppUserRepository appUserRepository;

    public CreateListCommandHandler(ToDoListRepository toDoListRepository, AppUserRepository appUserRepository) {
        this.toDoListRepository = toDoListRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    @Override
    public String handle(CreateListCommand command) {
        AppUser user = appUserRepository
                .findByEmail(SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ToDoList newList = new ToDoList(command,user);
        toDoListRepository.save(newList);
        return "List has been created";
    }
}
