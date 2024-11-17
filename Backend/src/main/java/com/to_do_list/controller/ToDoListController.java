package com.to_do_list.controller;

import com.to_do_list.components.CommandDispatcher;
import com.to_do_list.components.QueryDispatcher;
import com.to_do_list.cqrs.task.command.UpdateTaskCommand;
import com.to_do_list.cqrs.to_do_list.command.CreateListCommand;
import com.to_do_list.cqrs.to_do_list.command.DeleteListCommand;
import com.to_do_list.cqrs.to_do_list.command.UpdateListCommand;
import com.to_do_list.cqrs.to_do_list.dto.*;
import com.to_do_list.cqrs.to_do_list.query.GetListByIdQuery;
import com.to_do_list.cqrs.to_do_list.query.GetListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/list")
@RestController
public class ToDoListController {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;

    @Autowired
    public ToDoListController(CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }

    @PostMapping("/create")
    public CreateListResponse createList(@RequestBody CreateListDto createListDto) {
        CreateListCommand command = new CreateListCommand(createListDto);
        return commandDispatcher.dispatch(command);

    }

   @GetMapping("/all")
    public List<GetAllListResponse> getAllList() {
        GetListQuery getListQuery = new GetListQuery();
        return queryDispatcher.dispatch(getListQuery);
   }

   @GetMapping("{id}")
    public GetListByIdResponse getListById(@PathVariable Integer id) {
        GetListByIdQuery getListByIdQuery = new GetListByIdQuery(id);
        return queryDispatcher.dispatch(getListByIdQuery);

   }
   @DeleteMapping("/delete/{id}")
    public void deleteList(@PathVariable Integer id) {
       DeleteListCommand deleteListCommand = new DeleteListCommand(id);
       commandDispatcher.dispatch(deleteListCommand);
   }

   @PutMapping("/update/{id}")
    public CreateListResponse updateList(@PathVariable Integer id, @RequestBody UpdateListDto updateListDto) {
       UpdateListCommand updateListCommand = new UpdateListCommand(id,updateListDto);
       return commandDispatcher.dispatch(updateListCommand);
   }
}
