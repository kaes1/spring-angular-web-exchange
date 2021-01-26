export class ApiEndpoints {
  public static readonly REGISTER = 'api/register';
  public static readonly REGISTER_CONFIRMATION = 'api/register/confirm';
  public static readonly AUTH_LOGIN = 'api/auth/login';
  public static readonly AUTH_REFRESH = 'api/auth/refresh';

  public static readonly CURRENCIES = 'api/currencies';
  public static readonly ADMIN_CURRENCY_CONFIGURATION = 'api/currencies/configuration';
  public static readonly ADMIN_CURRENCY_ACTIVATE = 'api/currencies/configuration';

  public static readonly CURRENCY_RATES_LATEST = 'api/currencyRates/latest';
  public static readonly CURRENCY_RATES_HISTORY = 'api/currencyRates/history';

  public static readonly USER_CURRENCY_BALANCE = 'api/userBalance';

  public static readonly OPERATIONS_ADD_FUNDS = 'api/operations/addFunds';
  public static readonly OPERATIONS_TRADE_CURRENCY = 'api/operations/tradeCurrency';
  public static readonly OPERATIONS_HISTORY = 'api/operations/history';
}
