import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {TradeCurrencyComponent} from '../trade-currency/trade-currency.component';
import {AddFundsComponent} from '../add-funds/add-funds.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CurrencyService} from '../service/currency.service';
import {Currency} from '../model/currency.model';

@Component({
  selector: 'app-nav-menu',
  templateUrl: './nav-menu.component.html',
  styleUrls: ['./nav-menu.component.scss']
})
export class NavMenuComponent implements OnInit {

  loggedIn = false;
  isAdmin = false;
  username = '';
  currencies: Currency[] = [];
  baseCurrency: string = '';

  constructor(private authService: AuthService,
              private modalService: NgbModal,
              private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe(loggedIn => this.loggedIn = loggedIn);
    this.authService.getUsername().subscribe(username => this.username = username);
    this.authService.getRole().subscribe(role => this.isAdmin = role === 'ROLE_ADMIN');
    this.currencyService.getBaseCurrency().subscribe(baseCurrency => this.baseCurrency = baseCurrency.currencyCode);
    this.currencyService.getCurrencies().subscribe(currencies => this.currencies = currencies);
  }

  logout() {
    this.authService.logout();
  }

  onCurrencySelected() {
    this.currencyService.setBaseCurrency({currencyCode: this.baseCurrency});
    this.currencyService.fetchLatestCurrencyRateList(this.baseCurrency);
  }

  tradeCurrency() {
    this.modalService.open(TradeCurrencyComponent, {centered: true});
  }

  addFunds() {
    this.modalService.open(AddFundsComponent, {centered: true});
  }

}
