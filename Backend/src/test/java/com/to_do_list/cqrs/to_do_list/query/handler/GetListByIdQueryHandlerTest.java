package com.to_do_list.cqrs.to_do_list.query.handler;

import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.cqrs.to_do_list.dto.GetListByIdResponse;
import com.to_do_list.cqrs.to_do_list.query.GetListByIdQuery;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class GetListByIdQueryHandlerTest {

    @InjectMocks
    private GetListByIdQueryHandler getListByIdQueryHandler;
    @Mock
    private AppUserService appUserService;
    @Mock
    private ToDoListRepository toDoListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GetListByIdQueryHandler_ShouldThrowExceptionWhenListDoNotExist() {
        //given
        given(toDoListRepository.findById(any())).willReturn(Optional.empty());
        //when
        //then
        assertThrows(ToDoListNotFoundException.class, () -> {getListByIdQueryHandler.handle(new GetListByIdQuery(1));});
        verify(toDoListRepository,times(1)).findById(any());

    }
    @Test
    void GetListByIdQueryHandler_ShouldThrowExceptionWhenListIsNotAssignedToUser(){
        //given
        AppUser user = preparedUser();
        ToDoList toDoList = new ToDoList();
        toDoList.setAppUser(new AppUser("wrong@test.pl", "wrong", new AppRole()));
        given(toDoListRepository.findById(any())).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(user);
        // when
        // then
        assertThrows(WrongCredentialException.class, () -> getListByIdQueryHandler.handle(new GetListByIdQuery(any())));
        verify(appUserService, times(1)).getCurrentUser();
        verify(toDoListRepository, times(1)).findById(any());
    }

    @Test
    void GetListByIdQueryHandler_ShouldReturnListWithTwoTask(){
        //given
        AppUser user = preparedUser();
        ToDoList toDoList = preparedList();
        toDoList.setAppUser(user);
        given(toDoListRepository.findById(1)).willReturn(Optional.of(toDoList));
        given(appUserService.getCurrentUser()).willReturn(user);

        //when
        GetListByIdResponse result = getListByIdQueryHandler.handle(new GetListByIdQuery(1));

        //then
        verify(appUserService,times(1)).getCurrentUser();
        verify(toDoListRepository,times(1)).findById(1);
        assertThat(result.getName(),is(toDoList.getName()));
        assertThat(result.getTasks(),hasSize(2));
        assertThat(result.getId(),is(1));
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