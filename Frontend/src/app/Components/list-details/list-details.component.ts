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


@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [ToastModule,TableModule,CommonModule,InputTextModule,FormsModule,ButtonModule],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.css'
})
export class ListDetailsComponent implements OnInit {

  currentListId: number = 0
  listDetails: GetListByIdResponse = {id: 0, name: '', tasks: []}
  createdTaskValue: string =""
  constructor(private activeRoute: ActivatedRoute,private router: Router,private listService: ListService,private messageService: MessageService,private taskService:TaskService ){

  }
  ngOnInit(): void {
    this.getListById()
  }

  public getListById(){

    this.activeRoute.params.subscribe(params => {this.currentListId = params['id']})
    this.listService.getListById(this.currentListId).subscribe({
      next: response => {
        this.listDetails = response
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
}
