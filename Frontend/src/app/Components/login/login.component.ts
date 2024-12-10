import { Component } from '@angular/core';

import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { ButtonModule } from 'primeng/button';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [InputGroupAddonModule,InputGroupModule,ButtonModule,ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm = this.formBuilder.group({
    email: ['',Validators.required,Validators.email],
    passowrd: ['', Validators.required]
  })

  constructor(private formBuilder: FormBuilder){
    
  }
}
