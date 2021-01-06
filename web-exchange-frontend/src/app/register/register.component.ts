import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api/api.service';
import {RegisterRequest} from '../model/register-request.model';
import {ApiEndpoints} from '../api/api-endpoints';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  email = '';
  username = '';
  password = '';

  registerFailed = false;
  registerFailedMessage = '';

  constructor(private apiService: ApiService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  register() {
    this.registerFailed = false;

    const registerRequest: RegisterRequest = {
      email: this.email,
      username: this.username,
      password: this.password
    };

    this.apiService.post(ApiEndpoints.REGISTER, registerRequest).subscribe(response => {
      console.log('Got response!');
      console.log(response);
      this.router.navigate(['/login']);
    }, error => {
      console.log('Got error!');
      console.log(error);
      this.registerFailed = true;
      this.registerFailedMessage = error.message;
    });
  }
}
