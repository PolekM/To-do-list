import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { getAllListReposne } from '../../models/list/getAllListReposne';
import { ListService } from '../../Services/list.service';
import { Card, CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ListAddComponent } from "../list-add/list-add.component";
import { Subscription } from 'rxjs';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CreateListResponse } from '../../models/list/CreateListResponse';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CardModule, ButtonModule, ListAddComponent,ToastModule, FormsModule,InputTextModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit,OnDestroy{

  listResponse: getAllListReposne[] = []
  editedList: number | null = null
  private subscription: Subscription = {} as Subscription;
  constructor(private listService: ListService, private messageService: MessageService){
  }

  
  ngOnInit(): void {
    this.getUserList()
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public getUserList(){
    this.subscription = this.listService.getUserLists().subscribe(response => this.listResponse = response);
  }

  public deleteList(currentList:CreateListResponse){
    this.listService.deleteList(currentList.id).subscribe(
      () => {
        this.messageService.add({severity: 'success', summary: 'Success', detail: `You Are Deleted:${currentList.name}`})
        this.listResponse = this.listResponse.filter(list => list.id!==currentList.id)
      },
      (error) =>{
        this.messageService.add({severity:'error', summary:'Error',detail:error.message})
      }
    )

    }
    public cancelEdit(){
      this.editedList = null;
      
    }

    public enableEdit(id:number){
      this.editedList = id
    }

  listChangeEmit(){
    this.getUserList()
  }
}
