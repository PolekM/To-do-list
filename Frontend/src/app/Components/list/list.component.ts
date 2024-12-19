import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { getAllListReposne } from '../../models/list/getAllListReposne';
import { ListService } from '../../Services/list.service';
import { Card, CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ListAddComponent } from "../list-add/list-add.component";
import { Subscription } from 'rxjs';
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CardModule, ButtonModule, ListAddComponent],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit,OnDestroy{

  listResponse: getAllListReposne[] = []
  private subscription: Subscription = {} as Subscription;
  constructor(private listService: ListService){
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

  listChangeEmit(){
    this.getUserList()
  }
}
