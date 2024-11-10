package com.to_do_list.cqrs.to_do_list.dto;

import com.to_do_list.entity.ToDoList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateListResponse {

    private Integer id;
    private String name;

    public CreateListResponse(ToDoList toDoList){
        this.id = toDoList.getId();
        this.name = toDoList.getName();
    }
}
