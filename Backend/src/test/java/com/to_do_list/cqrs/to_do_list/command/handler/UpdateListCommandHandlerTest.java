package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.to_do_list.command.DeleteListCommand;
import com.to_do_list.cqrs.to_do_list.command.UpdateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListResponse;
import com.to_do_list.cqrs.to_do_list.dto.UpdateListDto;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UpdateListCommandHandlerTest {

    @Mock
    private ToDoListRepository toDoListRepository;
    @Mock
    private AppUserService appUserService;
    @InjectMocks
    private UpdateListCommandHandler updateListCommandHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void UpdateListCommandHandler_ShouldThrowExceptionWhenListDoNotExist() {
        //given
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(ToDoListNotFoundException.class,() -> updateListCommandHandler.handle(new UpdateListCommand(any(),new UpdateListDto())));
        verify(toDoListRepository).findById(any());
    }
    @Test
    void UpdateListCommandHandler_ShouldThrowWrongCredentialExceptionWhenListIsNotAssignedToUser(){
        //given
        ToDoList toDoList = preparedList();
        AppUser appUser = new AppUser();
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(appUser);
        //when

        //then
        assertThrows(WrongCredentialException.class,() -> updateListCommandHandler.handle(new UpdateListCommand(any(),new UpdateListDto())));
        verify(toDoListRepository).findById(any());
        verify(appUserService).getCurrentUser();
    }

    @Test
    void UpdateListCommandHandler_ShouldUpdateListName(){
        //given
        ToDoList toDoList = preparedList();
        AppUser appUser = toDoList.getAppUser();

        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(appUser);
        given(toDoListRepository.save(any())).willReturn(toDoList);

        UpdateListDto updateListDto = new UpdateListDto();
        updateListDto.setName("new test");
        //when
        CreateListResponse response = updateListCommandHandler.handle(new UpdateListCommand(1, updateListDto));

        //then
        verify(toDoListRepository).findById(any());
        verify(appUserService).getCurrentUser();
        verify(toDoListRepository).save(any());
        assertThat(response.getName(),is("new test"));

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
