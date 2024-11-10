package com.to_do_list.cqrs.to_do_list.query;

import com.to_do_list.cqrs.common.Query;
import com.to_do_list.cqrs.to_do_list.dto.GetAllListResponse;

import java.util.List;

public class GetListQuery implements Query<List<GetAllListResponse>> { }
