package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.to_do_list.command.DeleteListCommand;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class DeleteListCommandHandlerTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private ToDoListRepository toDoListRepository;

    @InjectMocks
    private DeleteListCommandHandler deleteListCommandHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void DeleteListCommandHandler_ShouldThrowListNotFoundExceptionWhenListDoNotExist() {
        //given
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(ToDoListNotFoundException.class,() -> deleteListCommandHandler.handle(new DeleteListCommand(any())));
        verify(toDoListRepository).findById(any());
    }

    @Test
    void DeleteListCommandHandler_ShouldThrowWrongCredentialExceptionWhenListIsNotAssignedToUser(){
        //given
        ToDoList toDoList = preparedList();
        AppUser appUser = new AppUser();
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(appUser);
        //when

        //then
        assertThrows(WrongCredentialException.class,() -> deleteListCommandHandler.handle(new DeleteListCommand(any())));
        verify(toDoListRepository).findById(any());
        verify(appUserService).getCurrentUser();
    }

    @Test
    void DeleteListCommandHandler_ShouldDeleteList(){
        //given
        ToDoList toDoList = preparedList();
        AppUser appUser = toDoList.getAppUser();
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(appUser);
        //when
        Void result = deleteListCommandHandler.handle(new DeleteListCommand(1));
        //then
        verify(toDoListRepository).findById(any());
        verify(appUserService).getCurrentUser();
        verify(toDoListRepository).delete(any());
        Assertions.assertNull(result);
    }

    public AppUser preparedUser(){
        return new AppUser("test@test.pl", "test", new AppRole());
    }

    public ToDoList preparedList(){
        ToDoList preparedList = new ToDoList();
        preparedList.setId(1);
        preparedList.setName("test");
        preparedList.setAppUser(preparedUser());
        preparedList.setTasks(List.of(new Task(),new Task()));

        return preparedList;
    }

}