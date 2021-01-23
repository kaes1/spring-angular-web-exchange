import { Component, OnInit } from '@angular/core';
import {ApiEndpoints} from '../api/api-endpoints';
import {ApiService} from '../api/api.service';
import {ActivatedRoute} from '@angular/router';
import {HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-register-confirmation',
  templateUrl: './register-confirmation.component.html',
  styleUrls: ['./register-confirmation.component.scss']
})
export class RegisterConfirmationComponent implements OnInit {

  success = false;
  failed = false;

  constructor(private apiService: ApiService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const token = params.token;

      const httpParams = new HttpParams()
        .set('token', token);

      this.apiService.post(ApiEndpoints.REGISTER_CONFIRMATION, null, httpParams).subscribe(() => {
        console.log('Got response!');
        this.success = true;
      }, error => {
        console.log('Got error!');
        console.log(error);
        this.failed = true;
      });
    });
  }
}
