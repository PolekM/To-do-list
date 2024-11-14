package com.to_do_list.cqrs.task.command;

import com.to_do_list.cqrs.common.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteTaskCommand implements Command {

    private Integer id;
}
