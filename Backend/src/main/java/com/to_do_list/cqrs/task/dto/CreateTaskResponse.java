package com.to_do_list.cqrs.task.dto;

import com.to_do_list.entity.Task;
import lombok.Data;

@Data
public class CreateTaskResponse {

    private Integer id;
    private String description;

    public CreateTaskResponse(Task task){
        this.id = task.getId();
        this.description = task.getDescription();
    }

}
