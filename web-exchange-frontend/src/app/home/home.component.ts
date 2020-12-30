import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiService} from '../core/api.service';
import {Currency} from '../model/currency.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  currencies: Currency[] = [];

  constructor(private apiService: ApiService) {
  }

  ngOnInit(): void {
    this.fetchTestData();
  }

  private fetchTestData() {
    this.apiService.get<Currency>('api/currencies/PLN').subscribe(response => {
      console.log(response);
    });
    this.apiService.get<Currency[]>('api/currencies').subscribe(response => {
      console.log(response);
      this.currencies = response;
    });
  }

}
