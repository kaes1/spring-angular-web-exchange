import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  login = '';
  password = '';

  loginFailed = false;
  loginFailedMessage = '';

  constructor(private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  submitLogin() {
    this.loginFailed = false;

    this.authService.login(this.login, this.password).subscribe(response => {
      this.router.navigate(['/']);
    }, error => {
      this.loginFailed = true;
      this.loginFailedMessage = error.message;
    });
  }
}
