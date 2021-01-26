import {Injectable} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {ApiService} from '../api/api.service';
import {CurrencyRateHistory} from '../model/currency-rate-history.model';
import {ApiEndpoints} from '../api/api-endpoints';
import {GraphPoint} from '../model/graph-point.model';
import {GraphSeries} from '../model/graph-series.model';
import {DatePipe} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class GraphService {

  private graphDataSubject = new ReplaySubject<GraphSeries>(1);

  constructor(private apiService: ApiService) {
  }

  public getGraphData(): Observable<GraphSeries> {
    return this.graphDataSubject.asObservable();
  }

  public fetchGraphData(baseCurrency: string, targetCurrency: string, from: string, to: string) {
    let params = new HttpParams();
    params = params.append('baseCurrencyCode', baseCurrency);
    params = params.append('targetCurrencyCode', targetCurrency);
    params = params.append('from', from);
    params = params.append('to', to);

    this.apiService.get<CurrencyRateHistory>(ApiEndpoints.CURRENCY_RATES_HISTORY, params).subscribe((currencyRateHistory: CurrencyRateHistory) => {
      let graphSeriesList: GraphSeries = {
        name: targetCurrency,
        series: [],
      };
      for (let i = 0; i < currencyRateHistory.currencyRates.length; i++) {
        let datetime = new DatePipe('en-US').transform(new Date(currencyRateHistory.currencyRates[i].dateTime), 'dd/MM/yy, HH:mm');
        let name = (datetime != null ? datetime : '');
        let graphPoint: GraphPoint = {
          name: name,
          value: currencyRateHistory.currencyRates[i].rate,
        };
        graphSeriesList.series.push(graphPoint);
      }
      this.graphDataSubject.next(graphSeriesList);
    });
  }
}
