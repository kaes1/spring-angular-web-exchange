import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {CurrencyService} from '../service/currency.service';
import {WalletEntry} from '../model/wallet-entry.model';
import {combineLatest} from 'rxjs';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.scss']
})
export class WalletComponent implements OnInit {

  isLoggedIn = false;
  userWalletEntries: WalletEntry[] = [];
  baseCurrency: string = '';

  constructor(private authService: AuthService,
              private currencyService: CurrencyService) {
  }

  ngOnInit() {
    this.authService.getLoggedIn().subscribe(loggedIn => this.isLoggedIn = loggedIn);
    this.getWalletEntries();
  }

  public getWalletEntries(){
    combineLatest([this.currencyService.getLatestCurrencyRateList(), this.currencyService.getUserCurrencyBalanceList()]).subscribe(
      ([latestCurrencyRateList, userCurrencyBalancelist]) => {
        console.log(latestCurrencyRateList.currencyRates);
        console.log(userCurrencyBalancelist);

        this.userWalletEntries = [];
        this.baseCurrency = latestCurrencyRateList.baseCurrencyCode;

        for (let i = 0; i < latestCurrencyRateList.currencyRates.length; i++) {
          for (let j = 0; j < userCurrencyBalancelist.length; j++) {
            if (latestCurrencyRateList.currencyRates[i].targetCurrencyCode == userCurrencyBalancelist[j].currencyCode) {
              let entry: WalletEntry = {
                currencyCode: userCurrencyBalancelist[j].currencyCode,
                rate: latestCurrencyRateList.currencyRates[i].rate,
                amount: userCurrencyBalancelist[j].amount,
              };
              this.userWalletEntries.push(entry);
            }
          }
        }
        console.log(this.userWalletEntries);
      }
    );
  }
}