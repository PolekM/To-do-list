package com.to_do_list.cqrs.to_do_list.command;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.to_do_list.dto.UpdateListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateListCommand implements Command {

    private Integer id;
    private UpdateListDto updateListDto;
}
