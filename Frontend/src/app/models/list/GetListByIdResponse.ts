export interface GetListByIdResponse{
    id: number;
    name: string;
    tasks: GetListByIdResponse[];
}