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
  availableFunds: number = 0;

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
    combineLatest([this.currencyService.getLatestCurrencyRateList(), this.currencyService.getUserCurrencyBalanceList()]).subscribe(
      ([latestCurrencyRateList, userCurrencyBalancelist]) => {
        this.userWalletEntries = [];
        let userFunds = userCurrencyBalancelist.find(element => element.currencyCode == this.baseCurrency)?.amount;
        this.availableFunds = userFunds || 0;

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
