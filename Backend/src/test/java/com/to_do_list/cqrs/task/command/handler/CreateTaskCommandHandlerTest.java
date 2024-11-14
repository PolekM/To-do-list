package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.task.command.CreateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskDto;
import com.to_do_list.cqrs.task.dto.CreateTaskResponse;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.TaskRepository;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateTaskCommandHandlerTest {

    @Mock
    AppUserService appUserService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    ToDoListRepository toDoListRepository;
    @InjectMocks
    CreateTaskCommandHandler createTaskCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CreateTaskCommandHandler_ShouldThrowListNotFoundExceptionWhenListIsNotExist(){
        //given
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(ToDoListNotFoundException.class,()->createTaskCommandHandler.handle(new CreateTaskCommand(new CreateTaskDto())));
        verify(toDoListRepository,times(1)).findById(any());
    }

    @Test
    public void CreateTaskCommandHandler_ShouldThrowWrongCredentialExceptionWhenUserIsNotListOwner(){
        //given
        given(appUserService.getCurrentUser()).willReturn(new AppUser());
        given(toDoListRepository.findById(any())).willReturn(Optional.of(preparedList()));
        //when

        //then
        assertThrows(WrongCredentialException.class,() -> createTaskCommandHandler.handle(new CreateTaskCommand(new CreateTaskDto())));
        verify(appUserService,times(1)).getCurrentUser();
    }

    @Test
    public void CreateTaskCommandHandler_ShouldReturnNewAddedTask(){
        //given
        ToDoList toDoList = preparedList();
        AppUser appUser = toDoList.getAppUser();
        Task task = preparedTask();
        given(appUserService.getCurrentUser()).willReturn(appUser);
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(taskRepository.save(any())).willReturn(task);
        //when
        CreateTaskResponse result = createTaskCommandHandler.handle(new CreateTaskCommand(new CreateTaskDto()));
        //then
        verify(appUserService,times(1)).getCurrentUser();
        verify(taskRepository,times(1)).save(any());
        verify(toDoListRepository,times(1)).findById(any());
        assertThat(result.getDescription(),is("test"));
    }

    public AppUser preparedUser(){
        return new AppUser("test@test.pl", "test", new AppRole());
    }

    public ToDoList preparedList(){
        CreateListDto createListDto = new CreateListDto();
        createListDto.setName("test");
        CreateListCommand command = new CreateListCommand(createListDto);
        AppUser appUser = preparedUser();
        return new ToDoList(command,appUser);
    }

    public Task preparedTask(){
        Task task = new Task();
        task.setId(1);
        task.setDescription("test");
        return task;
    }
}