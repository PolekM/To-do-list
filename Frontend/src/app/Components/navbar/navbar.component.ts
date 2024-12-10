import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';
@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MenubarModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

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
          label: 'Log in',
          icon: 'pi pi-sign-in',
          route: '/login'
      },
      {
        label: 'register',
        icon: 'pi pi-sign-out',
        route: '/register'
    },
  
  ]
  }
}
