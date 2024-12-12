import { Component } from '@angular/core';

import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { ButtonModule } from 'primeng/button';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthenticationService } from '../../Services/authentication.service';
import { LoginDto } from '../../models/authentication/LoginDto';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [InputGroupAddonModule,InputGroupModule,ButtonModule,ReactiveFormsModule,CommonModule,HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm = this.formBuilder.group({
    email: ['',[Validators.required,Validators.email]],
    passowrd: ['', Validators.required]
  })

 

  constructor(private formBuilder: FormBuilder,private authenticationService: AuthenticationService, private router: Router){
    
  }

  public authenticate(){
    const loginDto: LoginDto = {email:this.loginForm.value.email || '', password:this.loginForm.value.passowrd || ''};
    this.authenticationService.authenticate(loginDto).subscribe(response => {this.authenticationService.storageTokenToLocalStorage(response.token); this.router.navigate(['/list'])})
  }

 
}
