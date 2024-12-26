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
import { UpdateListDto } from '../../models/list/UpdateListDto';
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CardModule, ButtonModule, ListAddComponent,ToastModule, FormsModule,InputTextModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit,OnDestroy{

  listResponse: getAllListReposne[] = []
  editedListId: number | null = null
  prevListName: string = ''
  editedListName: UpdateListDto = {} as UpdateListDto
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
    public updateListName(){
      if(this.editedListId!=null){
        this.listService.updateListName(this.editedListName,this.editedListId).subscribe(
          {next: response => {
            const index = this.listResponse.findIndex(list => list.id === this.editedListId);
          if (index !== -1) {
            this.listResponse[index].name = response.name;
           }
            this.messageService.add({severity: 'success', summary: 'Success', detail: `Your List:${response.name} has Been Edited`})
            this.editedListId = null;
          },
          error: error =>{
            this.messageService.add({severity:'error', summary:'Error',detail:error.message})
            this.editedListId = null;
          }

        }
      )
    }
      
    }

    public cancelEdit(list: CreateListResponse){
      this.editedListId = null;
      list.name = this.prevListName
      
    }

    public enableEdit(list:CreateListResponse){
      this.editedListId = list.id;
      this.prevListName = list.name
      this.editedListName.name = list.name
  
    }

  listChangeEmit(){
    this.getUserList()
  }
}
