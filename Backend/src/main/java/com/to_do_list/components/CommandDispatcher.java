package com.to_do_list.components;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.common.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandDispatcher {

private final Map<Class<?extends Command>, CommandHandler<?,?>> handlers= new HashMap<>();

public <C extends Command,R> void registerHandler(Class<C> commandClass, CommandHandler<C,R> handler) {
    handlers.put(commandClass,handler);
}
@SuppressWarnings("unchecked")
public <C extends Command,R> R dispatch(C command) {
    CommandHandler<C,R> handler = (CommandHandler<C,R>) handlers.get(command.getClass());
    if(handler != null) {
        return handler.handle(command);
    }
    throw new IllegalArgumentException("No handler found for command: " + command.getClass());
}

}
