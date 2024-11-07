package com.to_do_list.cqrs.user.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String email;
    private String password;

}
