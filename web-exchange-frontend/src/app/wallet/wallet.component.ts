import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {UserCurrencyBalanceModel} from "../model/user-currency-balance.model";
import {CurrencyService} from "../service/currency.service";
import {CurrencyRate} from "../model/currency-rate.model";

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.scss']
})
export class WalletComponent implements OnInit {

  isLoggedIn = false;
  userCurrencyBalanceList: UserCurrencyBalanceModel[] = [];
  latestCurrencyRateList: CurrencyRate[] = [];

  constructor(private authService: AuthService,
              private currencyService: CurrencyService) {
  }

  ngOnInit() {
    this.authService.getLoggedIn().subscribe(loggedIn => this.isLoggedIn = loggedIn);
    this.currencyService.getUserCurrencyBalanceList().subscribe(userCurrencyBalanceList => {
      this.userCurrencyBalanceList = userCurrencyBalanceList;
      console.log(userCurrencyBalanceList);
    });
    this.currencyService.getLatestCurrencyRateList().subscribe(latestCurrencyRateList => {
      this.latestCurrencyRateList = latestCurrencyRateList.currencyRates;
      console.log(latestCurrencyRateList);
    });
  }
}
