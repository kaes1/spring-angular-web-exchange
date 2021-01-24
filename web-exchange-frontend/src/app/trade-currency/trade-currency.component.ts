import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Currency} from '../model/currency.model';
import {CurrencyService} from '../service/currency.service';
import {HttpParams} from '@angular/common/http';
import {FunctionEnum} from '../model/function-enum.model';
import {TradeCurrencyRequest} from '../model/trade-currency-request.model';

@Component({
  selector: 'app-trade-currency',
  templateUrl: './trade-currency.component.html',
  styleUrls: ['./trade-currency.component.scss']
})
export class TradeCurrencyComponent implements OnInit {

  model: TradeCurrencyRequest = {soldCurrencyCode: '', boughtCurrencyCode: '', sellAmount: 1};
  rate: number = 1;
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
      this.failedMessage = error;
    });
  }

  onCurrencySelected() {
    if (this.model.soldCurrencyCode != this.model.boughtCurrencyCode && this.model.boughtCurrencyCode != '' && this.model.soldCurrencyCode != '') {

      let params = new HttpParams();
      params = params.append('baseCurrencyCode', this.model.soldCurrencyCode);

      this.currencyService.fetchLatestCurrencyRateList(FunctionEnum.tradeCurrency, params);
      this.currencyService.getLatestCurrencyRateList(FunctionEnum.tradeCurrency).subscribe(latestCurrencyList => {
        let dataForBoughtCurrency = latestCurrencyList.currencyRates.find(x => x.targetCurrencyCode == this.model.boughtCurrencyCode);
        this.rate = (dataForBoughtCurrency != null ? dataForBoughtCurrency.rate : 1);
      });
    }
  }

  onCancel() {
    this.activeModal.dismiss('Cancel');
  }

}
