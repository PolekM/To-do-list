package com.to_do_list.cqrs.task.dto;

import com.to_do_list.entity.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class TaskQueryResponse {

    Integer id;
    String description;
    Boolean isCompleted;

    public TaskQueryResponse(Task task){
        this.id = task.getId();
        this.description = task.getDescription();
        this.isCompleted = task.getIsCompleted();
    }
}
