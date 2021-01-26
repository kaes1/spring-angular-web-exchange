import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Currency} from '../model/currency.model';
import {CurrencyService} from '../service/currency.service';
import {TradeCurrencyRequest} from '../model/trade-currency-request.model';

@Component({
  selector: 'app-trade-currency',
  templateUrl: './trade-currency.component.html',
  styleUrls: ['./trade-currency.component.scss']
})
export class TradeCurrencyComponent implements OnInit {

  model: TradeCurrencyRequest = {soldCurrencyCode: '', boughtCurrencyCode: '', sellAmount: 0, rate: 0};
  currencies: Currency[] = [];

  failed = false;
  failedMessage: string = '';

  constructor(private activeModal: NgbActiveModal,
              private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(currencies => {
      this.currencies = currencies;
    });
  }

  onSubmit() {
    this.currencyService.tradeCurrency(this.model).subscribe(success => {
      this.activeModal.close(this.model);
    }, error => {
      console.log(error);
      this.failed = true;
      this.failedMessage = error.message;
    });
  }

  onCurrencySelected() {
    if (this.model.soldCurrencyCode != this.model.boughtCurrencyCode && this.model.boughtCurrencyCode != '' && this.model.soldCurrencyCode != '') {
      this.currencyService.getLatestCurrencyRateBetween(this.model.soldCurrencyCode, this.model.boughtCurrencyCode).subscribe(currencyRate => {
        this.model.rate = currencyRate?.rate || 1;
      });
    }
  }

  onCancel() {
    this.activeModal.dismiss('Cancel');
  }

}
