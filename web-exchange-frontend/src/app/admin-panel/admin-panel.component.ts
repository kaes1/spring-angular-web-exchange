import {Component, OnInit} from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {CurrencyConfiguration} from '../model/admin/currency-configuration.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {EditCurrencyConfigurationComponent} from '../edit-currency-configuration/edit-currency-configuration.component';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.scss']
})
export class AdminPanelComponent implements OnInit {

  currencyConfigurationList: CurrencyConfiguration[] = [];

  constructor(private apiService: ApiService,
              private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.fetchCurrencyConfiguration();
  }

  fetchCurrencyConfiguration() {
    this.apiService.get<CurrencyConfiguration[]>(ApiEndpoints.ADMIN_CURRENCY_CONFIGURATION).subscribe(response => {
      const sortedResponse = response.sort((a, b) => a.currencyCode.localeCompare(b.currencyCode));
      this.currencyConfigurationList = sortedResponse;
    });
  }

  editCurrency(configuration: CurrencyConfiguration) {
    console.log(configuration);
    const modalRef = this.modalService.open(EditCurrencyConfigurationComponent, {centered: true});
    const component: EditCurrencyConfigurationComponent = modalRef.componentInstance;
    component.initialCurrencyConfig = configuration;
    modalRef.closed.subscribe(result => {
      this.fetchCurrencyConfiguration();
    });
  }

}
