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
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CardModule, ButtonModule, ListAddComponent,ToastModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit,OnDestroy{

  listResponse: getAllListReposne[] = []
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
    
    
  

  listChangeEmit(){
    this.getUserList()
  }
}
