import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { getAllListReposne } from '../models/list/getAllListReposne';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  listBaseUrl: string = "http://localhost:8080/list"

  constructor(private http: HttpClient) { }

  public getUserLists():Observable<getAllListReposne[]>{
    return this.http.get<getAllListReposne[]>(`${this.listBaseUrl}/all`)
  }
}
