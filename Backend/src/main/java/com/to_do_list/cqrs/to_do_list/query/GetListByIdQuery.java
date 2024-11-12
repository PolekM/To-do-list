package com.to_do_list.cqrs.to_do_list.query;

import com.to_do_list.cqrs.common.Query;
import com.to_do_list.cqrs.to_do_list.dto.GetListByIdResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetListByIdQuery implements Query<GetListByIdResponse> {

    private Integer id;
}
