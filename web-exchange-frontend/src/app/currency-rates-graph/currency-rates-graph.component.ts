import {Component, OnInit} from '@angular/core';
import {GraphService} from '../service/graph.service';
import {GraphSeries} from '../model/graph-series.model';
import {CurrencyService} from '../service/currency.service';
import {Currency} from '../model/currency.model';
import {interval} from 'rxjs';

@Component({
  selector: 'app-currency-rates-graph',
  templateUrl: './currency-rates-graph.component.html',
  styleUrls: ['./currency-rates-graph.component.scss']
})
export class CurrencyRatesGraphComponent implements OnInit {
  graphData: GraphSeries[] = [];
  currencies: Currency[] = [];
  selectedParams = {baseCurrency: '', targetCurrency: '', from: '', to: ''};

  constructor(private graphService: GraphService,
              private currencyService: CurrencyService) {
  }

  ngOnInit() {
    this.currencyService.getCurrencies().subscribe(currencies => {
      this.currencies = currencies;
    });

  }

  fetchGraph(baseCurrency: string, targetCurrency: string, from: string, to: string) {
    this.graphService.fetchGraphData(baseCurrency, targetCurrency, from, to);
    this.graphService.getGraphData().subscribe(graphSeries => {
      this.graphData = [];
      this.graphData.push(graphSeries);
    });
  }

  fetchGraphInInterval() {
    this.fetchGraph(this.selectedParams.baseCurrency, this.selectedParams.targetCurrency, this.selectedParams.from, this.selectedParams.to);
    const source = interval(10000);
    source.subscribe(() => {
      this.fetchGraph(this.selectedParams.baseCurrency, this.selectedParams.targetCurrency, this.selectedParams.from, this.selectedParams.to);
    });
  }

}
