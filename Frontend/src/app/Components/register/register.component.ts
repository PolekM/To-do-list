import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { AuthenticationService } from '../../Services/authentication.service';
import { RegisterDto } from '../../models/authentication/RegisterDto';
import { Router } from '@angular/router';
import { DialogModule } from 'primeng/dialog';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule,ReactiveFormsModule,InputGroupAddonModule,DialogModule,ToastModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm = this.formBuilder.group({
    email: ['',[Validators.required,Validators.email]],
    password: ['',Validators.required],
    repeatPassword:['',[Validators.required,this.passwordMatchValidator]]
  },)

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService,private router:Router,private messageService:MessageService){

  }

  register(){
    const registerDto: RegisterDto = {
      email: this.registerForm.value.email || '', 
      password:this.registerForm.value.password || '', 
      repeatPassword: this.registerForm.value.repeatPassword || ''
    }
    this.authenticationService.register(registerDto).subscribe(
      {next: response =>
        {
          this.messageService.add({severity: 'success', summary: 'Success', detail: `${response}`})
          this.registerForm.reset()
        },
      error: err =>{ 
        this.messageService.add({severity:'error', summary:'Error',detail:err.message})
        }
      })
  }
  passwordMatchValidator(form: AbstractControl):ValidationErrors | null{
    const password = form.parent?.get('password')?.value;
    const repeatPassword = form.parent?.get('repeatPassword')?.value;
    console.log(password+" "+repeatPassword)
    return password === repeatPassword ? null : { passwordMismatch: true };
  }
}
