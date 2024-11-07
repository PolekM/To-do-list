package com.to_do_list.cqrs.user.command;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.user.dto.RegisterDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterCommand implements Command {

    private RegisterDto registerDto;

}
