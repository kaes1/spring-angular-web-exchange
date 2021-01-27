import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {CurrencyService} from '../service/currency.service';
import {Currency} from '../model/currency.model';
import {AddFundsRequest} from '../model/add-funds-request.model';

@Component({
  selector: 'app-add-funds',
  templateUrl: './add-funds.component.html',
  styleUrls: ['./add-funds.component.scss']
})
export class AddFundsComponent implements OnInit {

  model: AddFundsRequest = {currencyCode: '', amount: 0};
  currencies: Currency[] = [];

  failed = false;
  failedMessage = '';

  constructor(private activeModal: NgbActiveModal,
              private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(currencies => {
      this.currencies = currencies;
    });
    this.currencyService.getBaseCurrency().subscribe(baseCurrency => {
      this.model.currencyCode = baseCurrency;
    });
  }

  onSubmit() {
    this.currencyService.addFunds(this.model).subscribe(success => {
      this.activeModal.close(this.model);
    }, error => {
      console.log(error);
      this.failed = true;
      this.failedMessage = error.message;
    });
  }

  onCancel() {
    this.activeModal.dismiss('Cancel');
  }

}
