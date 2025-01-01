import { Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { ListComponent } from './Components/list/list.component';
import { HomeComponent } from './Components/home/home.component';
import { loginRegisterAccessGuard } from './Guards/login-register-access.guard';
import { authGuard } from './Guards/auth.guard';
import { ListDetailsComponent } from './Components/list-details/list-details.component';

export const routes: Routes = [
    {
        path:"", component:HomeComponent
    },
    {
        path:"home", component:HomeComponent
    },
    {
        path: "login", component:LoginComponent, canActivate:[loginRegisterAccessGuard]
    },
    {
        path: "register", component: RegisterComponent, canActivate:[loginRegisterAccessGuard]
    },
    {
        path: "list", component:ListComponent, canActivate:[authGuard]
    },
    {
        path:"list/:id", component:ListDetailsComponent, canActivate:[authGuard]
    }
];
