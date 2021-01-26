import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api/api.service';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  loggedIn = false;
  isAdmin = false;

  constructor(private apiService: ApiService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
    this.authService.getRole().subscribe(role => this.isAdmin = role === 'ROLE_ADMIN');
  }

}
