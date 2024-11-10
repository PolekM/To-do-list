package com.to_do_list.cqrs.to_do_list.query.handler;

import com.to_do_list.cqrs.common.QueryHandler;
import com.to_do_list.cqrs.to_do_list.dto.GetAllListResponse;
import com.to_do_list.cqrs.to_do_list.query.GetListQuery;
import com.to_do_list.entity.AppUser;
import com.to_do_list.entity.ToDoList;
import com.to_do_list.repository.ToDoListRepository;
import com.to_do_list.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListQueryHandler implements QueryHandler<GetListQuery, List<GetAllListResponse>> {

    private final AppUserService appUserService;
    private final ToDoListRepository toDoListRepository;

    @Autowired
    public GetListQueryHandler(AppUserService appUserService, ToDoListRepository toDoListRepository) {
        this.appUserService = appUserService;
        this.toDoListRepository = toDoListRepository;
    }

    @Override
    public List<GetAllListResponse> handle(GetListQuery query) {
        AppUser currentUser = appUserService.getCurrentUser();
        List<ToDoList> allByAppUserId = toDoListRepository.findAllByAppUserId(currentUser.getId());
        return allByAppUserId.stream().map(GetAllListResponse::new).toList();
    }
}
