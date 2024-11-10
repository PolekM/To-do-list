package com.to_do_list.cqrs.to_do_list.dto;

import com.to_do_list.entity.ToDoList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAllListResponse {

    Integer id;
    String name;

    public GetAllListResponse(ToDoList toDoList) {
        this.id =toDoList.getId();
        this.name = toDoList.getName();
    }

}
