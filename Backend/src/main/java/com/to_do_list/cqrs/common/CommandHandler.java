package com.to_do_list.cqrs.common;

public interface CommandHandler <C extends Command,R>{

    R handle(C command);
}
