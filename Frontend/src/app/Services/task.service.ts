import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { CreateTaskDto } from '../models/Task/CreateTaskDto';
import { CreateTaskResponse } from '../models/Task/CreateTaskResponse';
import { UpdateTaskDto } from '../models/Task/UpdateTaskDto';

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
  public createTask(listId: number, description: string):Observable<CreateTaskResponse>{
    let newTask: CreateTaskDto = {listId,description}
    return this.http.post<CreateTaskResponse>(`${this.taskBaseUrl}/add`,newTask).pipe(
      catchError(
        error =>{
          return throwError(() => new Error(error.error?.message))
        }
      )
    )
  }

  public updateTask(taskId:number,isCompleted?: boolean, description?:string):Observable<CreateTaskResponse>{
    let updatedTask: UpdateTaskDto = {isCompleted,description}
    return this.http.put<CreateTaskResponse>(`${this.taskBaseUrl}/update/${taskId}`,updatedTask).pipe(
      catchError(
        error => {
          return throwError(() => new Error(error.error?.message))
        }
      )
    )
  }
}
