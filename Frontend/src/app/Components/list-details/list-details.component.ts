import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-details',
  standalone: true,
  imports: [],
  templateUrl: './list-details.component.html',
  styleUrl: './list-details.component.css'
})
export class ListDetailsComponent implements OnInit {

  id: number = 0
  constructor(private router: ActivatedRoute){

  }
  ngOnInit(): void {
    this.router.params.subscribe(params => {this.id = params['id']})
  }
}
