package com.to_do_list.cqrs.task.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTaskDto {
    private String description;
    private Boolean isCompleted;
}
