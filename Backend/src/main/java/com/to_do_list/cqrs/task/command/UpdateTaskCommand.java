package com.to_do_list.cqrs.task.command;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.task.dto.UpdateTaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateTaskCommand implements Command {

    private Integer id;
    private UpdateTaskDto updateTask;
}
