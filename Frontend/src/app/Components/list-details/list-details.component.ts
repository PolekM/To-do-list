import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ListService } from '../../Services/list.service';
import { GetListByIdResponse } from '../../models/list/GetListByIdResponse';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { CommonModule } from '@angular/common';

import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [ToastModule,TableModule,CommonModule],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.css'
})
export class ListDetailsComponent implements OnInit {

  currentListId: number = 0
  listDetails: GetListByIdResponse = {} as GetListByIdResponse
  constructor(private activeRoute: ActivatedRoute,private router: Router,private listService: ListService,private messageService: MessageService){

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
}
