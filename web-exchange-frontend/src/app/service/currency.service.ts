import {Injectable} from '@angular/core';
import {ApiService} from "../api/api.service";
import {ApiEndpoints} from "../api/api-endpoints";
import {Observable, ReplaySubject} from "rxjs";
import {UserCurrencyBalanceModel} from "../model/user-currency-balance.model";
import {LatestCurrencyRateList} from "../model/latest-currency-rate-list.model";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private userCurrencyBalanceListSubject = new ReplaySubject<UserCurrencyBalanceModel[]>(1);
  private latestCurrencyRateListSubject = new ReplaySubject<LatestCurrencyRateList>(1);

  constructor(private apiService: ApiService) {
    this.fetchUserCurrencyBalanceList();
    this.fetchLatestCurrencyRateList();
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
    this.apiService.get<LatestCurrencyRateList>(ApiEndpoints.LATEST_CURRENCY_RATE, baseCurrency).subscribe((latestCurrencyRateList: LatestCurrencyRateList) => {
      console.log(latestCurrencyRateList);
      this.latestCurrencyRateListSubject.next(latestCurrencyRateList);
    })
  }

  public getWalletEntries() {

  }
}
