import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {interval, Observable, ReplaySubject} from 'rxjs';
import {UserCurrencyBalanceModel} from '../model/user-currency-balance.model';
import {LatestCurrencyRateList} from '../model/latest-currency-rate-list.model';
import {HttpParams} from '@angular/common/http';
import {CurrencyRateHistory} from '../model/currency-rate-history.model';
import {Currency} from '../model/currency.model';
import {FunctionEnum} from '../model/function-enum.model';
import {TradeCurrencyRequest} from '../model/trade-currency-request.model';

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private userCurrencyBalanceListSubject = new ReplaySubject<UserCurrencyBalanceModel[]>(1);
  private latestCurrencyRateListForWalletSubject = new ReplaySubject<LatestCurrencyRateList>(1);
  private latestCurrencyRateListForTradeSubject = new ReplaySubject<LatestCurrencyRateList>(1);
  private currencyRateHistorySubject = new ReplaySubject<CurrencyRateHistory>(1);
  private currenciesSubject = new ReplaySubject<Currency[]>(1);

  userBaseCurrency = 'CZK';

  constructor(private apiService: ApiService) {
    this.fetchCurrencies();

    let params = new HttpParams();
    params = params.append('baseCurrencyCode', this.userBaseCurrency);

    this.fetchLatestCurrencyRateList(FunctionEnum.wallet, params);
    const source = interval(10000);
    source.subscribe(() => {
      this.fetchLatestCurrencyRateList(FunctionEnum.wallet, params);
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

  public getLatestCurrencyRateList(func: FunctionEnum): Observable<LatestCurrencyRateList> {
    if (func == FunctionEnum.wallet)
      return this.latestCurrencyRateListForWalletSubject.asObservable();
    else
      return this.latestCurrencyRateListForTradeSubject.asObservable();

  }

  public fetchLatestCurrencyRateList(func: FunctionEnum, baseCurrency: HttpParams) {
    this.apiService.get<LatestCurrencyRateList>(ApiEndpoints.CURRENCY_RATES_LATEST, baseCurrency).subscribe((latestCurrencyRateList: LatestCurrencyRateList) => {
      console.log(latestCurrencyRateList);
      if (func == FunctionEnum.wallet)
        this.latestCurrencyRateListForWalletSubject.next(latestCurrencyRateList);
      else if (func == FunctionEnum.tradeCurrency)
        this.latestCurrencyRateListForTradeSubject.next(latestCurrencyRateList);
    });
  }

  public getCurrencyRateHistory(): Observable<CurrencyRateHistory> {
    return this.currencyRateHistorySubject.asObservable();
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
    return new Observable(observer => {
      this.apiService.post(ApiEndpoints.OPERATIONS_TRADE_CURRENCY, model)
        .subscribe(result => {
          this.fetchUserCurrencyBalanceList();
          observer.next('Success');
        }, error => {
          observer.error(error.error);
        });
    });
  }

}
