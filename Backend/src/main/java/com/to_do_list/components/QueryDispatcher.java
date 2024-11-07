package com.to_do_list.components;

import com.to_do_list.cqrs.common.Command;
import com.to_do_list.cqrs.common.Query;
import com.to_do_list.cqrs.common.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryDispatcher {

private Map<Class<? extends Query<?>>, QueryHandler<?,?>> handlers = new HashMap<>();

public <Q extends Query<R>,R> void registerHandler(Class<Q> queryClass,QueryHandler<?,?> handler){
        handlers.put(queryClass,handler);
    }
    @SuppressWarnings("unchecked")
    public <Q extends Query<R>,R> R dispatch(Q query){
        QueryHandler<Q,R> queryHandler = (QueryHandler<Q, R>) handlers.get(query.getClass());
        if(queryHandler != null){
            return queryHandler.handle(query);
        }
        throw new IllegalArgumentException("No handler found for query class " + query.getClass());

    }
}
