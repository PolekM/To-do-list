import { Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { ListComponent } from './Components/list/list.component';

export const routes: Routes = [
    {
        path: "login", component:LoginComponent
    },
    {
        path: "register", component: RegisterComponent
    },
    {
        path: "list", component:ListComponent
    }
];
