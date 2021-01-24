import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {interval, Observable, ReplaySubject} from 'rxjs';
import {UserCurrencyBalanceModel} from '../model/user-currency-balance.model';
import {LatestCurrencyRateList} from '../model/latest-currency-rate-list.model';
import {HttpParams} from '@angular/common/http';
import {CurrencyRateHistory} from '../model/currency-rate-history.model';
import {Currency} from '../model/currency.model';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private userCurrencyBalanceListSubject = new ReplaySubject<UserCurrencyBalanceModel[]>(1);
  private latestCurrencyRateListSubject = new ReplaySubject<LatestCurrencyRateList>(1);
  private currencyRateHistorySubject = new ReplaySubject<CurrencyRateHistory>(1);
  private currenciesSubject = new ReplaySubject<Currency[]>(1);

  constructor(private apiService: ApiService) {
    this.fetchCurrencies();
    this.fetchLatestCurrencyRateList();
    const source = interval(10000);
    source.subscribe(() => {
      this.fetchLatestCurrencyRateList();
    });
  }

  public getUserCurrencyBalanceList(): Observable<UserCurrencyBalanceModel[]> {
    return this.userCurrencyBalanceListSubject.asObservable();
  }

  public fetchUserCurrencyBalanceList() {
    this.apiService.get<UserCurrencyBalanceModel[]>(ApiEndpoints.USER_CURRENCY_BALANCE).subscribe((userCurrencyBalanceList: UserCurrencyBalanceModel[]) => {
      console.log(userCurrencyBalanceList);
      this.userCurrencyBalanceListSubject.next(userCurrencyBalanceList);
    });
  }

  public getLatestCurrencyRateList(): Observable<LatestCurrencyRateList> {
    return this.latestCurrencyRateListSubject.asObservable();
  }

  public fetchLatestCurrencyRateList(baseCurrency?: HttpParams) {
    this.apiService.get<LatestCurrencyRateList>(ApiEndpoints.CURRENCY_RATES_LATEST, baseCurrency).subscribe((latestCurrencyRateList: LatestCurrencyRateList) => {
      console.log(latestCurrencyRateList);
      this.latestCurrencyRateListSubject.next(latestCurrencyRateList);
    });
  }

  public getCurrencyRateHistory(): Observable<CurrencyRateHistory> {
    return this.currencyRateHistorySubject.asObservable();
  }

  public fetchCurrencyRateHistory(params?: HttpParams) {
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

}
