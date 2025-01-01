import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { getAllListReposne } from '../models/list/getAllListReposne';
import { CreateListResponse } from '../models/list/CreateListResponse';
import { CreateListDto } from '../models/list/CreateListDto';
import { UpdateListDto } from '../models/list/UpdateListDto';
import { GetListByIdResponse } from '../models/list/GetListByIdResponse';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  listBaseUrl: string = "http://localhost:8080/list"

  constructor(private http: HttpClient) { }

  public getUserLists():Observable<getAllListReposne[]>{
    return this.http.get<getAllListReposne[]>(`${this.listBaseUrl}/all`)
  }
  public createList(createListDto: CreateListDto):Observable<CreateListResponse>{
    return this.http.post<CreateListResponse>(`${this.listBaseUrl}/create`,createListDto).pipe(
      catchError( error =>{return throwError(()=> new Error(error.error?.message))}))
  }

  public deleteList(listId: number):Observable<void>{
    return this.http.delete<void>(`${this.listBaseUrl}/delete/${listId}`).pipe(
      catchError(
        error => {
          return throwError(() => new Error(error.error?.message))
        }
      )
    )
  }

  public updateListName(updateListDto: UpdateListDto,id:number):Observable<CreateListResponse>{
    return this.http.put<CreateListResponse>(`${this.listBaseUrl}/update/${id}`,updateListDto).pipe(
        catchError(
          error =>{
          return  throwError( () => new Error(error.error?.message))
        }
      )
    )
  }
  public getListById(idList:number): Observable<GetListByIdResponse>{
    return this.http.get<GetListByIdResponse>(`${this.listBaseUrl}/${idList}`).pipe(
      catchError(
        error =>{
          return throwError(() => error)
        }
      )
    )
  }
}
