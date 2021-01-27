import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Currency} from '../model/currency.model';
import {CurrencyService} from '../service/currency.service';
import {TradeCurrencyRequest} from '../model/trade-currency-request.model';
import {UserCurrencyBalance} from '../model/user-currency-balance.model';

@Component({
  selector: 'app-trade-currency',
  templateUrl: './trade-currency.component.html',
  styleUrls: ['./trade-currency.component.scss']
})
export class TradeCurrencyComponent implements OnInit {

  model: TradeCurrencyRequest = {soldCurrencyCode: '', boughtCurrencyCode: '', sellAmount: 0, rate: 0};
  allAvailableCurrencies: Currency[] = [];
  userCurrencyBalanceList: UserCurrencyBalance[] = [];

  sellingBalanceBefore = 0;
  buyingBalanceBefore = 0;
  sellingBalanceAfter = 0;
  buyingBalanceAfter = 0;

  outdatedCurrencyRateDetected = false;
  failed = false;
  failedMessage = '';

  constructor(private activeModal: NgbActiveModal,
              private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(currencies => {
      this.allAvailableCurrencies = currencies;
    });

    this.currencyService.getBaseCurrency().subscribe(baseCurrency => {
      this.model.soldCurrencyCode = baseCurrency;
    });

    this.currencyService.getUserCurrencyBalanceList().subscribe(userCurrencyBalanceList => {
      this.userCurrencyBalanceList = userCurrencyBalanceList;
      console.log(this.userCurrencyBalanceList);
    });
  }

  onSubmit() {
    this.outdatedCurrencyRateDetected = false;
    this.failed = false;
    this.currencyService.tradeCurrency(this.model).subscribe(success => {
      this.activeModal.close(this.model);
    }, error => {
      console.log(error);
      if (error.status === 409) {
        // Handle outdated currency rate error
        this.outdatedCurrencyRateDetected = true;
        this.fetchLatestCurrencyRate();
      } else {
        this.failed = true;
        this.failedMessage = error.message;
      }
    });
  }

  onSoldCurrencySelected() {
    if (this.model.soldCurrencyCode === this.model.boughtCurrencyCode) {
      this.model.boughtCurrencyCode = '';
    }
    if (this.model.boughtCurrencyCode !== '' && this.model.soldCurrencyCode !== '') {
      this.fetchLatestCurrencyRate();
    }
    this.calculateBalances();
  }

  onBoughtCurrencySelected() {
    if (this.model.soldCurrencyCode === this.model.boughtCurrencyCode) {
      this.model.soldCurrencyCode = '';
    }
    if (this.model.boughtCurrencyCode !== '' && this.model.soldCurrencyCode !== '') {
      this.fetchLatestCurrencyRate();
    }
    this.calculateBalances();
  }

  onSellAmountChange() {
    this.calculateBalances();
  }

  calculateBalances() {
    if (!this.model.soldCurrencyCode) {
      this.sellingBalanceBefore = 0;
      this.sellingBalanceAfter = 0;
    } else {
      const soldBalance = this.userCurrencyBalanceList.find(x => x.currencyCode === this.model.soldCurrencyCode);
      this.sellingBalanceBefore = soldBalance?.amount || 0;
      this.sellingBalanceAfter = this.sellingBalanceBefore - this.model.sellAmount;
    }

    if (!this.model.boughtCurrencyCode) {
      this.buyingBalanceBefore = 0;
      this.buyingBalanceAfter = 0;
    } else {
      const boughtBalance = this.userCurrencyBalanceList.find(x => x.currencyCode === this.model.boughtCurrencyCode);
      this.buyingBalanceBefore = boughtBalance?.amount || 0;
      this.buyingBalanceAfter = this.model.soldCurrencyCode && this.model.sellAmount
        ? this.buyingBalanceBefore + this.model.sellAmount * this.model.rate
        : this.buyingBalanceBefore;
    }
  }

  onCancel() {
    this.activeModal.dismiss('Cancel');
  }

  private fetchLatestCurrencyRate() {
    this.currencyService.getLatestCurrencyRateBetween(this.model.soldCurrencyCode, this.model.boughtCurrencyCode).subscribe(currencyRate => {
      this.model.rate = currencyRate?.rate || 1;
      this.calculateBalances();
    });
  }
}
