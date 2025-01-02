import { TaskQueryResponse } from "../Task/TaskQueryResponse";

export interface GetListByIdResponse{
    id: number;
    name: string;
    tasks: TaskQueryResponse[];
}