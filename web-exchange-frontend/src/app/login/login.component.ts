import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
  }


  login() {
    this.authService.login(this.username, this.password).subscribe(response => {
      console.log('Got response!');
      console.log(response);
    }, error => {
      console.log('Got error!');
      console.log(error);
    });
  }

}
