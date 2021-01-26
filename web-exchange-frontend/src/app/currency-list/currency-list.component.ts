import {Component, OnInit} from '@angular/core';
import {CurrencyService} from '../service/currency.service';
import {CurrencyRate} from '../model/currency-rate.model';

@Component({
  selector: 'app-currency-list',
  templateUrl: './currency-list.component.html',
  styleUrls: ['./currency-list.component.scss']
})
export class CurrencyListComponent implements OnInit {

  entries: CurrencyRate[] = [];
  baseCurrency: string = '';

  constructor(private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.getBaseCurrency().subscribe(baseCurrency => {
      this.baseCurrency = baseCurrency.currencyCode;
    });
    this.currencyService.getLatestCurrencyRateList().subscribe(list => {
      this.entries = list.currencyRates;
    });
  }

}
