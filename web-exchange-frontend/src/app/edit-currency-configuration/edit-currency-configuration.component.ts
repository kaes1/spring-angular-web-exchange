import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {ModifyCurrencyRequest} from '../model/admin/modify-currency-request.model';
import {CurrencyConfiguration} from '../model/admin/currency-configuration.model';
import {ApiEndpoints} from '../api/api-endpoints';
import {ApiService} from '../api/api.service';

@Component({
  selector: 'app-edit-currency-configuration',
  templateUrl: './edit-currency-configuration.component.html',
  styleUrls: ['./edit-currency-configuration.component.scss']
})
export class EditCurrencyConfigurationComponent implements OnInit {

  @Input() initialCurrencyConfig: CurrencyConfiguration = {active: false, country: '', currencyCode: ''};
  model: ModifyCurrencyRequest = {active: false, country: '', currencyCode: ''};
  failed = false;
  failedMessage = '';
  inProgress = false;

  constructor(private activeModal: NgbActiveModal,
              private apiService: ApiService) {
  }

  ngOnInit(): void {
    this.model.active = this.initialCurrencyConfig.active;
    this.model.currencyCode = this.initialCurrencyConfig.currencyCode;
    this.model.country = this.initialCurrencyConfig.country;
  }

  onSubmit() {
    this.inProgress = true;
    this.apiService.post(ApiEndpoints.ADMIN_CURRENCY_MODIFY, this.model).subscribe(response => {
      console.log('Currency modified succesfully');
      this.activeModal.close(this.model);
      this.inProgress = false;
    }, error => {
      this.inProgress = false;
      console.log(error);
      this.failed = true;
      this.failedMessage = error;
    });
  }

  onCancel() {
    this.activeModal.dismiss('Cancel');
  }

}
