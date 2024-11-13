package com.to_do_list.cqrs.task.command;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.task.dto.CreateTaskDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateTaskCommand implements Command {

    private CreateTaskDto createTaskDto;
}
