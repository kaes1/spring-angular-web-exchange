import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api/api.service';
import {RegisterRequest} from '../model/register-request.model';
import {ApiEndpoints} from '../api/api-endpoints';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  email = '';
  username = '';
  password = '';

  inProgress = false;
  registerSuccess = false;
  registerFailed = false;
  registerFailedMessage = '';

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
  }

  register() {
    this.registerFailed = false;
    this.inProgress = true;

    const registerRequest: RegisterRequest = {
      email: this.email,
      username: this.username,
      password: this.password
    };

    this.apiService.post(ApiEndpoints.REGISTER, registerRequest).subscribe(response => {
      console.log('Got response!');
      console.log(response);
      this.inProgress = false;
      this.registerSuccess = true;
      this.registerFailed = false;
    }, error => {
      console.log('Got error!');
      console.log(error);
      this.inProgress = false;
      this.registerFailed = true;
      this.registerFailedMessage = error.message;
    });
  }
}
