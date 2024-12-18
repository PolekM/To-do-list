import { Component } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ListService } from '../../Services/list.service';
import { CreateListDto } from '../../models/list/CreateListDto';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CreateListResponse } from '../../models/list/CreateListResponse';

@Component({
  selector: 'app-list-add',
  standalone: true,
  imports: [ButtonModule,DialogModule,InputTextModule,FormsModule,ToastModule],
  templateUrl: './list-add.component.html',
  styleUrl: './list-add.component.css'
})
export class ListAddComponent {

  visible: boolean = false;
  createListDto: CreateListDto = {name: undefined}
  createListResponse: CreateListResponse = {} as CreateListResponse
  constructor(private listService: ListService, private messageService: MessageService){

  }


  showDialog() {
    this.visible = true;
}

  createList(){
    this.visible = false;
  this.listService.createList(this.createListDto).subscribe({
    next: response => {
      this.createListResponse,
      this.messageService.add({severity: 'success', summary: 'Success', detail: `created list: ${response.name}`})
    },
    error: err =>{
      this.messageService.add({severity:'error', summary:'Error',detail:err.message})
    },
    complete: () =>{this.createListDto.name = undefined}
    })
}
}

