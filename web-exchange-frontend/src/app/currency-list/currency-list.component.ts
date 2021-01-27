import {Component, OnInit} from '@angular/core';
import {CurrencyService} from '../service/currency.service';
import {combineLatest} from 'rxjs';
import {CurrencyListEntry} from '../model/currency-list-entry.model';

@Component({
  selector: 'app-currency-list',
  templateUrl: './currency-list.component.html',
  styleUrls: ['./currency-list.component.scss']
})
export class CurrencyListComponent implements OnInit {

  entries: CurrencyListEntry[] = [];
  baseCurrency: string = '';

  constructor(private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.getBaseCurrency().subscribe(baseCurrency => {
      this.baseCurrency = baseCurrency;
    });
    this.getEntries();
  }

  getEntries() {
    combineLatest([this.currencyService.getCurrencies(), this.currencyService.getLatestCurrencyRates()]).subscribe(
      ([currencies, latestCurrencyRates]) => {
        this.entries = [];

        currencies.forEach(currency => {
          let currencyRate = latestCurrencyRates.currencyRates.find(el => el.targetCurrencyCode == currency.currencyCode);

          let entry: CurrencyListEntry = {
            currencyCode: currency.currencyCode,
            rate: currencyRate?.rate || 1,
            country: currency.country,
          };
          this.entries.push(entry);
        });
        this.entries = this.entries.sort((a, b) => a.currencyCode.localeCompare(b.currencyCode));
      }
    );
  }

}
