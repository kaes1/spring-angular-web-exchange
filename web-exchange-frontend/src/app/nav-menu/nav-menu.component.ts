import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.scss']
})
export class NavMenuComponent implements OnInit {

  loggedIn = false;
  username = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
    this.authService.getUsername().subscribe(username => this.username = username);
  }

  logout() {
    this.authService.logout();
  }

}
