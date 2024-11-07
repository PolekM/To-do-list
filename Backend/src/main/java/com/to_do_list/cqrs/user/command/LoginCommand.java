package com.to_do_list.cqrs.user.command;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.user.dto.LoginDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginCommand implements Command {

    private LoginDto loginDto;

}
