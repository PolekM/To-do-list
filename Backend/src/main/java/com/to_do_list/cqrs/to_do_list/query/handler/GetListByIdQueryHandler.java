package com.to_do_list.cqrs.to_do_list.query.handler;

import com.to_do_list.cqrs.common.QueryHandler;
import com.to_do_list.cqrs.to_do_list.dto.GetListByIdResponse;
import com.to_do_list.cqrs.to_do_list.query.GetListByIdQuery;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.exception.List.ToDoListNotFoundException;
import com.to_do_list.exception.user.WrongCredentialException;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetListByIdQueryHandler implements QueryHandler<GetListByIdQuery, GetListByIdResponse> {

    private final AppUserService appUserService;
    private final ToDoListRepository toDoListRepository;

    @Autowired
    public GetListByIdQueryHandler(AppUserService appUserService, ToDoListRepository toDoListRepository) {
        this.appUserService = appUserService;
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public GetListByIdResponse handle(GetListByIdQuery query) {
        ToDoList list = toDoListRepository.findById(query.getId()).orElseThrow(() -> new ToDoListNotFoundException("List do not exist"));
        AppUser user = appUserService.getCurrentUser();
        if(!user.equals(list.getAppUser()))
            throw new WrongCredentialException("Wrong User Credential");

        return new GetListByIdResponse(list);
    }
}
