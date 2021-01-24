import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {WalletComponent} from './wallet/wallet.component';
import {RegisterConfirmationComponent} from './register-confirmation/register-confirmation.component';
import {UserNotLoggedInGuard} from './auth/user-not-logged-in.guard';
import {UserLoggedInGuard} from './auth/user-logged-in.guard';
import {OperationHistoryComponent} from './operation-history/operation-history.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'wallet', component: WalletComponent, canActivate: [UserLoggedInGuard]},
  {path: 'operation-history', component: OperationHistoryComponent, canActivate: [UserLoggedInGuard]},
  {path: 'login', component: LoginComponent, canActivate: [UserNotLoggedInGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [UserNotLoggedInGuard]},
  {path: 'register-confirmation/:token', component: RegisterConfirmationComponent},
  {path: '**', redirectTo: '/home'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
