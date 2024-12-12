import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';
import { AuthenticationService } from '../../Services/authentication.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MenubarModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  token: String = ''


  constructor(private authenticationService: AuthenticationService,private router: Router){}

  items: MenuItem[] | undefined;

  ngOnInit(): void {
    this.items = [
      {
          label: 'Home',
          icon: 'pi pi-home',
          route: '/list'
      },
      {
          label: 'List',
          icon: 'pi pi-list',
          route: '/list'
      },
      {
          label: 'Login',
          icon: 'pi pi-sign-in',
          route: '/login'
      },
      {
        label: 'Register',
        icon: 'pi pi-sign-out',
        route: '/register'
    },
    {
      label: 'Logout',
      icon: 'pi pi-sign-out',
      route: '/login',
      command: () => this.logout()
    }
  
  ]
  this.token = this.getToken()
  this.authenticationService.token$.subscribe(token => this.token = token)
  }
  public getToken(){
    return this.authenticationService.getToken();
  }

  public logout(){
    this.authenticationService.removeLocalStorageToken();
  }
}
