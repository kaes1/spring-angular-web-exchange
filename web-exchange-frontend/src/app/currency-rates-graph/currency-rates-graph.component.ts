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
  displayedGraphParams = {baseCurrency: '', targetCurrency: '', from: '', to: ''};

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
    this.fetchGraph(this.displayedGraphParams.baseCurrency, this.displayedGraphParams.targetCurrency, this.displayedGraphParams.from, this.displayedGraphParams.to);
    const source = interval(10000);
    source.subscribe(() => {
      this.fetchGraph(this.displayedGraphParams.baseCurrency, this.displayedGraphParams.targetCurrency, this.displayedGraphParams.from, this.displayedGraphParams.to);
    });
  }

  onBaseCurrencySelected() {
    if (this.selectedParams.baseCurrency == this.selectedParams.targetCurrency) {
      this.selectedParams.targetCurrency = '';
    }
  }

  onTargetCurrencySelected() {
    if (this.selectedParams.baseCurrency == this.selectedParams.targetCurrency) {
      this.selectedParams.baseCurrency = '';
    }
  }

  onFromSelected() {
    if (this.selectedParams.from >= this.selectedParams.to) {
      this.selectedParams.to = '';
    }
  }

  onToSelected() {
    if (this.selectedParams.from >= this.selectedParams.to) {
      this.selectedParams.from = '';
    }
  }

  onSubmit() {
    this.displayedGraphParams.baseCurrency = this.selectedParams.baseCurrency;
    this.displayedGraphParams.targetCurrency = this.selectedParams.targetCurrency;
    this.displayedGraphParams.from = this.selectedParams.from;
    this.displayedGraphParams.to = this.selectedParams.to;
    this.fetchGraphInInterval();
  }

}
