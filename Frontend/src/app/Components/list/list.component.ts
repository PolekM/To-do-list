import { Component, OnInit } from '@angular/core';
import { getAllListReposne } from '../../models/list/getAllListReposne';
import { ListService } from '../../Services/list.service';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit{

  listResponse: getAllListReposne[] = []
  constructor(private listService: ListService){

  }
  ngOnInit(): void {
    this.getUserList()
  }
  public getUserList(){
    return this.listService.getUserLists().subscribe(response => this.listResponse = response);
  }
}
