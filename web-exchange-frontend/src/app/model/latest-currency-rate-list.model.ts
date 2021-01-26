import {CurrencyRate} from "./currency-rate.model";

export interface LatestCurrencyRates {
  baseCurrencyCode: string;
  currencyRates: CurrencyRate[];
}
