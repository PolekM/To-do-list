package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.task.command.DeleteTaskCommand;
import com.to_do_list.cqrs.task.command.UpdateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskResponse;
import com.to_do_list.cqrs.task.dto.UpdateTaskDto;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.Task.TaskNotFoundException;
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
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UpdateTaskCommandHandlerTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AppUserService appUserService;
    @Mock
    private ToDoListRepository toDoListRepository;
    @InjectMocks
    private UpdateTaskCommandHandler updateTaskCommandHandler;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void UpdateTaskCommandHandler_ShouldThrowTaskNotFoundExceptionWhenTaskDoNotExist() {
        //given
        given(taskRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(TaskNotFoundException.class,()->updateTaskCommandHandler.handle(new UpdateTaskCommand(1,new UpdateTaskDto())));
        verify(taskRepository).findById(any());
    }

    @Test
    void UpdateTaskCommandHandler_ShouldThrowListNotFoundExceptionWhenListDoNotExist(){
        //given
        Task task = preparedTask();
        given(taskRepository.findById(any())).willReturn(Optional.of(task));
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(ToDoListNotFoundException.class,()->updateTaskCommandHandler.handle(new UpdateTaskCommand(1,new UpdateTaskDto())));
        verify(taskRepository).findById(any());
        verify(toDoListRepository).findById(any());
    }

    @Test
    void UpdateTaskCommandHandler_ShouldUpdateTask(){
        //given
        Task task = preparedTask();
        ToDoList toDoList = preparedList();
        AppUser appUser = toDoList.getAppUser();
        given(taskRepository.findById(any())).willReturn(Optional.of(task));
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(appUser);
        UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        updateTaskDto.setDescription("new description");
        updateTaskDto.setIsCompleted(true);
        //when
        CreateTaskResponse handle = updateTaskCommandHandler.handle(new UpdateTaskCommand(1, updateTaskDto));
        //then
        verify(taskRepository).findById(any());
        verify(appUserService).getCurrentUser();
        verify(taskRepository).findById(any());
        verify(taskRepository).save(any());
        assertThat(handle.getDescription(),is("new description"));
        assertThat(handle.getIsCompleted(),is(true));

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
        task.setList(preparedList());
        return task;
    }
    public AppUser preparedUser(){
        return new AppUser("test@test.pl", "test", new AppRole());
    }
}


