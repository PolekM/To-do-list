package com.to_do_list.cqrs.to_do_list.command.handler;

import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.CreateListDto;
import com.to_do_list.cqrs.to_do_list.dto.ToDoListCreateResponse;
import com.to_do_list.entity.AppRole;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.CreateListIllegalArgumentException;
import com.to_do_list.repository.AppUserRepository;
import com.to_do_list.repository.ToDoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CreateListCommandHandlerTest {


    @Mock
    private ToDoListRepository toDoListRepository;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private CreateListCommandHandler createListCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@test.pl");
    }

    @Test
    void CreateListCommandHandler_ShouldCreateList() {
        //given
        AppUser appUser = new AppUser("test@test.pl", "test", new AppRole());
        ToDoList toDoList = new ToDoList(initCreateListCommand(), appUser);
        given(appUserRepository.findByEmail(anyString())).willReturn(Optional.of(appUser));
        given(toDoListRepository.save(any())).willReturn(toDoList);
        //when
        ToDoListCreateResponse response = createListCommandHandler.handle(initCreateListCommand());
        //then
        assertThat(toDoList.getName(), is(response.getName()));
        verify(toDoListRepository, times(1)).save(any());
        verify(appUserRepository, times(1)).findByEmail(anyString());

    }

    @Test
    void CreateListCommandHandler_ShouldThrowExceptionWhenCommandNameIsNull() {
        //given
        CreateListCommand command = new CreateListCommand(new CreateListDto());
        //when

        //then
        assertThrows(CreateListIllegalArgumentException.class, () -> createListCommandHandler.handle(command));
    }

    @Test
    void CreateListCommandHandler_ShouldThrowExceptionWhenUserIsNotFound() {
        //given
        CreateListCommand command = initCreateListCommand();
        given(appUserRepository.findByEmail(anyString())).willReturn(Optional.empty());
        //when

        //then
        assertThrows(UsernameNotFoundException.class, () -> createListCommandHandler.handle(command));
    }

    public CreateListCommand initCreateListCommand() {
        CreateListDto createListDto = new CreateListDto();
        createListDto.setName("Test");
        return new CreateListCommand(createListDto);
    }
}