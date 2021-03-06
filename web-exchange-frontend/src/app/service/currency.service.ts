import {Injectable, OnDestroy} from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {interval, Observable, ReplaySubject, Subscription} from 'rxjs';
import {UserCurrencyBalance} from '../model/user-currency-balance.model';
import {LatestCurrencyRates} from '../model/latest-currency-rate-list.model';
import {HttpParams} from '@angular/common/http';
import {CurrencyRateHistory} from '../model/currency-rate-history.model';
import {Currency} from '../model/currency.model';
import {TradeCurrencyRequest} from '../model/trade-currency-request.model';
import {map, tap} from 'rxjs/operators';
import {AddFundsRequest} from '../model/add-funds-request.model';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService implements OnDestroy {

  private userCurrencyBalanceListSubject = new ReplaySubject<UserCurrencyBalance[]>(1);
  private latestCurrencyRatesSubject = new ReplaySubject<LatestCurrencyRates>(1);
  private currencyRateHistorySubject = new ReplaySubject<CurrencyRateHistory>(1);
  private currenciesSubject = new ReplaySubject<Currency[]>(1);
  private userBaseCurrencySubject = new ReplaySubject<string>(1);

  latestCurrencyRateSubscription: Subscription = new Subscription();

  constructor(private apiService: ApiService) {
    this.fetchCurrencies();
    this.setBaseCurrency('EUR');

    this.userBaseCurrencySubject.subscribe(newCurrency => {
      this.latestCurrencyRateSubscription.unsubscribe();
      this.fetchLatestCurrencyRateList(newCurrency);
      const source = interval(10000);
      this.latestCurrencyRateSubscription = source.subscribe(() => {
        this.fetchLatestCurrencyRateList(newCurrency);
      });
    });
  }

  ngOnDestroy(): void {
    this.latestCurrencyRateSubscription.unsubscribe();
  }

  public setBaseCurrency(newBaseCurrency: string) {
    this.userBaseCurrencySubject.next(newBaseCurrency);
  }

  public getBaseCurrency(): Observable<string> {
    return this.userBaseCurrencySubject.asObservable();
  }

  public getUserCurrencyBalanceList(): Observable<UserCurrencyBalance[]> {
    return this.userCurrencyBalanceListSubject.asObservable();
  }

  public fetchUserCurrencyBalanceList() {
    this.apiService.get<UserCurrencyBalance[]>(ApiEndpoints.USER_CURRENCY_BALANCE).subscribe((userCurrencyBalanceList: UserCurrencyBalance[]) => {
      console.log(userCurrencyBalanceList);
      this.userCurrencyBalanceListSubject.next(userCurrencyBalanceList);
    });
  }

  public getLatestCurrencyRateBetween(baseCurrency: string, targetCurrency: string) {
    let params = new HttpParams();
    params = params.append('baseCurrencyCode', baseCurrency);
    params = params.append('targetCurrencyCode', targetCurrency);

    return this.apiService.get<LatestCurrencyRates>(ApiEndpoints.CURRENCY_RATES_LATEST, params).pipe(
      map(latestCurrencyList => {
        return latestCurrencyList.currencyRates.find(x => x.targetCurrencyCode == targetCurrency);
      })
    );
  }

  public getLatestCurrencyRates(): Observable<LatestCurrencyRates> {
    return this.latestCurrencyRatesSubject.asObservable();
  }

  public fetchLatestCurrencyRateList(baseCurrency: string) {
    let params = new HttpParams();
    params = params.append('baseCurrencyCode', baseCurrency);

    this.apiService.get<LatestCurrencyRates>(ApiEndpoints.CURRENCY_RATES_LATEST, params).subscribe((latestCurrencyRateList: LatestCurrencyRates) => {
      this.latestCurrencyRatesSubject.next(latestCurrencyRateList);
    });
  }

  public fetchCurrencyRateHistory(params: HttpParams) {
    this.apiService.get<CurrencyRateHistory>(ApiEndpoints.CURRENCY_RATES_HISTORY, params).subscribe((currencyRateHistory: CurrencyRateHistory) => {
      console.log(currencyRateHistory);
      this.currencyRateHistorySubject.next(currencyRateHistory);
    });
  }

  public getCurrencies(): Observable<Currency[]> {
    return this.currenciesSubject.asObservable();
  }

  public fetchCurrencies() {
    this.apiService.get<Currency[]>(ApiEndpoints.CURRENCIES).subscribe((currencies: Currency[]) => {
      console.log(currencies);
      this.currenciesSubject.next(currencies);
    });
  }

  public tradeCurrency(model: TradeCurrencyRequest) {
    return this.apiService.post(ApiEndpoints.OPERATIONS_TRADE_CURRENCY, model).pipe(
      tap(response => {
        this.fetchUserCurrencyBalanceList();
      })
    );
  }

  public addFunds(model: AddFundsRequest) {
    return this.apiService.post(ApiEndpoints.OPERATIONS_ADD_FUNDS, model).pipe(
      tap(response => {
        this.fetchUserCurrencyBalanceList();
      })
    );
  }

}
