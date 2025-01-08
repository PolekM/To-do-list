import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

   taskBaseUrl: string = "http://localhost:8080/task"
  constructor(private http: HttpClient) { }

  public deleteTask(id:number):Observable<void>{
    return this.http.delete<void>(`${this.taskBaseUrl}/delete/${id}`).pipe(
      catchError(
        error =>{
          return throwError(() => new Error(error.error?.message))
        }
      )
    )
  }
}
