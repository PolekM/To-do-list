package com.to_do_list.cqrs.task.command.handler;

import com.to_do_list.cqrs.task.command.CreateTaskCommand;
import com.to_do_list.cqrs.task.dto.CreateTaskDto;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.repository.TaskRepository;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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
    public void CreateTaskCommandHandler_ShouldThrowExceptionWhenListIsNotExist(){
        //given
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(ToDoListNotFoundException.class,()->createTaskCommandHandler.handle(new CreateTaskCommand(new CreateTaskDto())));
        verify(toDoListRepository,times(1)).findById(any());

    }
    
}