package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.cqrs.to_do_list.dto.CreateListResponse;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.CreateListIllegalArgumentException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateListCommandHandlerTest {


    @Mock
    private ToDoListRepository toDoListRepository;
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private CreateListCommandHandler createListCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CreateListCommandHandler_ShouldCreateList() {
        //given
        AppUser appUser = new AppUser("test@test.pl", "test", new AppRole());
        ToDoList toDoList = new ToDoList(initCreateListCommand(), appUser);
        given(appUserService.getCurrentUser()).willReturn(appUser);
        given(toDoListRepository.save(any())).willReturn(toDoList);
        //when
        CreateListResponse response = createListCommandHandler.handle(initCreateListCommand());
        //then
        assertThat(toDoList.getName(), is(response.getName()));
        verify(toDoListRepository, times(1)).save(any());
        verify(appUserService, times(1)).getCurrentUser();

    }

    @Test
    void CreateListCommandHandler_ShouldThrowExceptionWhenCommandNameIsNull() {
        //given
        CreateListCommand command = new CreateListCommand(new CreateListDto());
        //when

        //then
        assertThrows(CreateListIllegalArgumentException.class, () -> createListCommandHandler.handle(command));
    }

    public CreateListCommand initCreateListCommand() {
        CreateListDto createListDto = new CreateListDto();
        createListDto.setName("Test");
        return new CreateListCommand(createListDto);
    }
}