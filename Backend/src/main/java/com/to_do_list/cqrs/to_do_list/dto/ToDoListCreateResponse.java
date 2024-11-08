package com.to_do_list.cqrs.to_do_list.dto;

import com.to_do_list.entity.ToDoList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoListCreateResponse {

    private Integer id;
    private String name;

    public ToDoListCreateResponse(ToDoList toDoList){
        this.id = toDoList.getId();
        this.name = toDoList.getName();
    }
}
