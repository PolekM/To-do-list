import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ListService } from '../../Services/list.service';
import { GetListByIdResponse } from '../../models/list/GetListByIdResponse';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';

import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { TaskService } from '../../Services/task.service';
import { TaskQueryResponse } from '../../models/Task/TaskQueryResponse';
import { Checkbox, CheckboxModule } from 'primeng/checkbox';


@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [ToastModule,TableModule,CommonModule,InputTextModule,FormsModule,ButtonModule,CheckboxModule],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.css'
})
export class ListDetailsComponent implements OnInit {

  currentListId: number = 0
  listDetails: GetListByIdResponse = {} as GetListByIdResponse
  createdTaskValue: string =""
  editedTaskId: number | null = null
  prevTaskName: string = ''
  editedTask: TaskQueryResponse = {} as TaskQueryResponse
  constructor(private activeRoute: ActivatedRoute,private router: Router,private listService: ListService,private messageService: MessageService,private taskService:TaskService ){

  }
  ngOnInit(): void {
    this.activeRoute.params.subscribe(params => {this.currentListId = params['id']})
    this.getListById()
  }

  public getListById(){
  
    this.listService.getListById(this.currentListId).subscribe({
      next: response => {
        this.listDetails = response
        this.listDetails.tasks.sort((a, b) => Number(a.isCompleted) - Number(b.isCompleted));
      },
      error: err => {
        this.messageService.add({severity:'error', summary:'Error', detail: err.error.message})
        if(err.status === 401){
          this.router.navigate(['/login'])
        }
      }
    })
   
  }
  public deleteTask(currentTask: TaskQueryResponse){
    this.taskService.deleteTask(currentTask.id).subscribe(
      () =>{
        this.messageService.add({severity: 'success', summary: 'Success', detail: `Your Task has been deleted`})
        this.listDetails.tasks = this.listDetails.tasks.filter(task => task.id!==currentTask.id)
      },
      (error) =>{
        this.messageService.add({severity:'error', summary:'Error',detail:error.message})
      }
    )
  }
  public createTask(){
    this.taskService.createTask(this.currentListId,this.createdTaskValue).subscribe({
      next: response =>{
        this.getListById()
        this.messageService.add({severity: 'success', summary: 'Success', detail: `Your Task has been created`})
      },
      error: err =>{
        this.messageService.add({severity:'error', summary:'Error',detail:err.message})
      }
    })
  }

  public changeTaskStatus(task: TaskQueryResponse){
    this.taskService.updateTask(task.id,task.isCompleted,undefined).subscribe({
      next: response =>{
        this.getListById()
        this.messageService.add({severity: 'success', summary: 'Success', detail: `Your Task has been updated`})
      },
      error:err =>{
        this.messageService.add({severity:'error', summary:'Error',detail:err.message})
      }
    })
  }
  public changeTaskDescription(task: TaskQueryResponse){
    this.taskService.updateTask(task.id,undefined,task.description).subscribe({
      next: response =>{
        this.getListById()
        this.messageService.add({severity: 'success', summary: 'Success', detail: `Your Task has been updated`})
        this.editedTaskId = null
      },
      error:err =>{
        this.messageService.add({severity:'error', summary:'Error',detail:err.message})
      }
    })
  }

  public enableEdit(task: TaskQueryResponse){
    this.editedTaskId = task.id
    this.prevTaskName = task.description
    this.editedTask.description = task.description 
  }
    public cancelEdit(task: TaskQueryResponse){
        this.editedTaskId = null;
        task.description = this.prevTaskName
        
      }
}
