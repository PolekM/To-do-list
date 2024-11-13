package com.to_do_list.cqrs.task.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateTaskDto {

    private Integer listId;
    private String description;
}
