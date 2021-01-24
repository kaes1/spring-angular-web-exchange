import {CurrencyRate} from './currency-rate.model';

export interface CurrencyRateHistory {
  baseCurrencyCode: string;
  targetCurrencyCode: string;
  from: string;
  to: string;
  currencyRates: CurrencyRate[];
}
