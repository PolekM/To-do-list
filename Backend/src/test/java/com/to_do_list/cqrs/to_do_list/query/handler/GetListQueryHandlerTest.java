package com.to_do_list.cqrs.to_do_list.query.handler;

import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.cqrs.to_do_list.dto.GetAllListResponse;
import com.to_do_list.cqrs.to_do_list.query.GetListQuery;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class GetListQueryHandlerTest {

    @InjectMocks
    private GetListQueryHandler getListQueryHandler;

    @Mock
    private AppUserService appUserService;

    @Mock
    private ToDoListRepository toDoListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void GetListQueryHandler_ShouldReturnEmptyList() {

        //given
        given(appUserService.getCurrentUser()).willReturn(preparedUser());
        List<ToDoList> emptyList = new ArrayList<>();
        given(toDoListRepository.findAllByAppUserId(any())).willReturn(emptyList);
        //when
        List<GetAllListResponse> result = getListQueryHandler.handle(new GetListQuery());
        //then
        assertThat(result, is(empty()));
        verify(toDoListRepository,times(1)).findAllByAppUserId(any());
        verify(appUserService,times(1)).getCurrentUser();

    }

    @Test
    void GetListQueryHandler_ShouldReturnListWithThreeElements(){
        //given
        given(appUserService.getCurrentUser()).willReturn(preparedUser());
        given(toDoListRepository.findAllByAppUserId(any())).willReturn(preparedList());
        //when
        List<GetAllListResponse> result = getListQueryHandler.handle(new GetListQuery());
        //then
        assertThat(result, hasSize(3));
        verify(toDoListRepository,times(1)).findAllByAppUserId(any());
        verify(appUserService,times(1)).getCurrentUser();

    }

    public List<ToDoList>preparedList(){
        List<ToDoList> preparedList = new ArrayList<>();
        preparedList.add(new ToDoList(new CreateListCommand(new CreateListDto()),preparedUser()));
        preparedList.add(new ToDoList(new CreateListCommand(new CreateListDto()),preparedUser()));
        preparedList.add(new ToDoList(new CreateListCommand(new CreateListDto()),preparedUser()));
        return preparedList;
    }

    public AppUser preparedUser(){
        return new AppUser("test@test.pl", "test", new AppRole());
    }

}