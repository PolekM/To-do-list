package com.to_do_list.cqrs.common;

public interface QueryHandler <Q extends Query<R>,R> {
    R handle(Q query);
}
