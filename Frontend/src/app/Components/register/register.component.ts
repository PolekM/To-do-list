import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ButtonModule,ReactiveFormsModule,InputGroupAddonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm = this.formBuilder.group({
    email: ['',[Validators.required,Validators.email]],
    password: ['',Validators.required],
    repeatPassword:['',[Validators.required,this.passwordMatchValidator]]
  },)

  constructor(private formBuilder: FormBuilder){

  }

  register(){

  }
  passwordMatchValidator(form: AbstractControl):ValidationErrors | null{
    const password = form.parent?.get('password')?.value;
    const repeatPassword = form.get('repeatPassword')?.value;
    
    return password === repeatPassword ? null : { passwordMismatch: true };
  }
}
