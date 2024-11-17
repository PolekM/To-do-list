package com.to_do_list.cqrs.to_do_list.command;

import com.to_do_list.cqrs.common.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteListCommand implements Command {

    private Integer id;
}
