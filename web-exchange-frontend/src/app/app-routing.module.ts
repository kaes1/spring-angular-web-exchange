import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {WalletComponent} from './wallet/wallet.component';
import {RegisterConfirmationComponent} from './register-confirmation/register-confirmation.component';
import {NotLoggedInGuard} from './auth/not-logged-in.guard';
import {UserLoggedInGuard} from './auth/user-logged-in.guard';
import {OperationHistoryComponent} from './operation-history/operation-history.component';
import {AdminLoggedInGuard} from './auth/admin-logged-in.guard';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'wallet', component: WalletComponent, canActivate: [UserLoggedInGuard]},
  {path: 'operation-history', component: OperationHistoryComponent, canActivate: [UserLoggedInGuard]},
  {path: 'login', component: LoginComponent, canActivate: [NotLoggedInGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [NotLoggedInGuard]},
  {path: 'register-confirmation/:token', component: RegisterConfirmationComponent},
  {path: 'admin-panel', component: AdminPanelComponent, canActivate: [AdminLoggedInGuard]},
  {path: '**', redirectTo: '/home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
