import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {CurrencyService} from '../service/currency.service';
import {WalletEntry} from '../model/wallet-entry.model';
import {combineLatest} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TradeCurrencyComponent} from '../trade-currency/trade-currency.component';
import {AddFundsComponent} from '../add-funds/add-funds.component';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.scss']
})
export class WalletComponent implements OnInit {

  userWalletEntries: WalletEntry[] = [];
  baseCurrency: string = '';

  constructor(private authService: AuthService,
              private currencyService: CurrencyService,
              private modalService: NgbModal) {
  }

  ngOnInit() {
    this.currencyService.getBaseCurrency().subscribe(baseCurrency => {
      this.baseCurrency = baseCurrency.currencyCode;
    });
    this.currencyService.fetchUserCurrencyBalanceList();
    this.getWalletEntries();
  }

  public getWalletEntries() {
    combineLatest([this.currencyService.getCurrencies(), this.currencyService.getLatestCurrencyRates(), this.currencyService.getUserCurrencyBalanceList()]).subscribe(
      ([currencies, latestCurrencyRates, userCurrencyBalancelist]) => {
        this.userWalletEntries = [];

        currencies.forEach(currency => {
          let balance = userCurrencyBalancelist.find(el => el.currencyCode == currency.currencyCode);
          let currencyRate = latestCurrencyRates.currencyRates.find(el => el.targetCurrencyCode == currency.currencyCode);

          let entry: WalletEntry = {
            currencyCode: currency.currencyCode,
            rate: currencyRate?.rate || 1,
            amount: balance?.amount || 0,
          };
          this.userWalletEntries.push(entry);
        });
      }
    );
  }

  tradeCurrency() {
    this.modalService.open(TradeCurrencyComponent, {centered: true});
  }

  addFunds() {
    this.modalService.open(AddFundsComponent, {centered: true});
  }

}
