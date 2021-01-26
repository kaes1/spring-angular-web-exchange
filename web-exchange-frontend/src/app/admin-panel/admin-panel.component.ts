import { Component, OnInit } from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {CurrencyConfiguration} from '../model/admin/currency-configuration.model';
import {ModifyCurrencyRequest} from '../model/admin/activate-currency-request.model';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {

  currencyConfigurationList: CurrencyConfiguration[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.fetchCurrencyConfiguration();
  }

  fetchCurrencyConfiguration() {
    this.apiService.get<CurrencyConfiguration[]>(ApiEndpoints.ADMIN_CURRENCY_CONFIGURATION).subscribe(response => {
      const sortedResponse = response.sort((a, b) => a.currencyCode.localeCompare(b.currencyCode))
      this.currencyConfigurationList = sortedResponse;
    });
  }

  activateCurrency(currencyConfig: CurrencyConfiguration) {

    const request: ModifyCurrencyRequest = {
      currencyCode: currencyConfig.currencyCode,
      active: true,
      country: currencyConfig.country
    };

    this.apiService.post(ApiEndpoints.ADMIN_CURRENCY_MODIFY, request).subscribe(response => {
      console.log('Currency modified succesfully');
      this.fetchCurrencyConfiguration();
    });
  }

}
