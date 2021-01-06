import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api/api.service';
import {Currency} from '../model/currency.model';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  loggedIn = false;

  constructor(private apiService: ApiService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
  }

  logout() {
    this.authService.logout();
  }

  testOpenEndpoint() {
    this.apiService.get('api/open').subscribe(response => {
      console.log(response);
    });
  }

  testSecureEndpoint() {
    this.apiService.get('api/secure').subscribe(response => {
      console.log(response);
    });
  }
}
