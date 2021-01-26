import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {JwtModule} from '@auth0/angular-jwt';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {NavMenuComponent} from './nav-menu/nav-menu.component';
import {RegisterComponent} from './register/register.component';
import {WalletComponent} from './wallet/wallet.component';
import {RegisterConfirmationComponent} from './register-confirmation/register-confirmation.component';
import {CurrencyRatesGraphComponent} from './currency-rates-graph/currency-rates-graph.component';
import {OperationHistoryComponent} from './operation-history/operation-history.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import { TradeCurrencyComponent } from './trade-currency/trade-currency.component';
import { AddFundsComponent } from './add-funds/add-funds.component';

export function tokenGetter() {
  return localStorage.getItem('jwt');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    NavMenuComponent,
    RegisterComponent,
    WalletComponent,
    RegisterConfirmationComponent,
    OperationHistoryComponent,
    AdminPanelComponent,
    CurrencyRatesGraphComponent,
    OperationHistoryComponent,
    TradeCurrencyComponent,
    AddFundsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgxChartsModule,
    JwtModule.forRoot({
      config: {
        tokenGetter,
        allowedDomains: ['localhost:8080'],
        disallowedRoutes: [],
      }
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
