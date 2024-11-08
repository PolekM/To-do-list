package com.to_do_list.cqrs.to_do_list.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class CreateListDto {

    private String name;
}
