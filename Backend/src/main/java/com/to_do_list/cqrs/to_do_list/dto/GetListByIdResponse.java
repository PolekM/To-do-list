package com.to_do_list.cqrs.to_do_list.dto;

import com.to_do_list.cqrs.task.dto.TaskQueryResponse;
import com.to_do_list.entity.Task;
import com.to_do_list.entity.ToDoList;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetListByIdResponse {

    private Integer id;
    private String name;
    private List<TaskQueryResponse> tasks;

    public GetListByIdResponse(ToDoList toDoList) {
        this.id = toDoList.getId();
        this.name = toDoList.getName();
        this.tasks = toDoList.getTasks().stream().map(TaskQueryResponse::new).toList();
    }
}
