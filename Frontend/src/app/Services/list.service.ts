import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { getAllListReposne } from '../models/list/getAllListReposne';
import { CreateListResponse } from '../models/list/CreateListResponse';
import { CreateListDto } from '../models/list/CreateListDto';

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
}
