import {CurrencyRate} from "./currency-rate.model";

export interface LatestCurrencyRateList {
  baseCurrencyCode: string;
  currencyRates: CurrencyRate[];
}
