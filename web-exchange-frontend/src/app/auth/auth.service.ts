import {Injectable, OnDestroy} from '@angular/core';
import {ApiService} from '../api/api.service';
import {LoginRequest} from '../model/login/login-request.model';
import {LoginResponse} from '../model/login/login-response.model';
import {interval, Observable, ReplaySubject, Subscription} from 'rxjs';
import {tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import {ApiEndpoints} from '../api/api-endpoints';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {

  private loggedInSubject = new ReplaySubject<boolean>(1);
  private usernameSubject = new ReplaySubject<string>(1);
  private roleSubject = new ReplaySubject<string>(1);

  private refreshSubscription: Subscription = new Subscription();

  constructor(private apiService: ApiService,
              private jwtHelper: JwtHelperService) {
    const token = localStorage.getItem('jwt') || undefined;
    const username = localStorage.getItem('username') || undefined;
    const role = localStorage.getItem('role') || undefined;
    const isLoggedIn = (token !== undefined && !this.jwtHelper.isTokenExpired(token));
    this.loggedInSubject.next(isLoggedIn);
    this.usernameSubject.next(isLoggedIn ? username : undefined);
    this.roleSubject.next(isLoggedIn ? role : undefined);

    const refreshInterval = interval(1000 * 60 * 10);
    this.refreshSubscription = refreshInterval.subscribe(() => this.refreshToken());
  }

  ngOnDestroy(): void {
    this.refreshSubscription.unsubscribe();
  }

  public getLoggedIn(): Observable<boolean> {
    return this.loggedInSubject.asObservable();
  }

  public getUsername(): Observable<string> {
    return this.usernameSubject.asObservable();
  }

  public getRole(): Observable<string> {
    return this.roleSubject.asObservable();
  }

  public logout() {
    localStorage.removeItem('jwt');
    this.loggedInSubject.next(false);
    this.usernameSubject.next(undefined);
  }

  public login(login: string, password: string): Observable<any> {
    const loginRequest: LoginRequest = {
      login, password
    };
    return this.apiService.post<LoginResponse>(ApiEndpoints.AUTH_LOGIN, loginRequest).pipe(
      tap(response => {
        localStorage.setItem('jwt', response.token);
        localStorage.setItem('username', response.username);
        localStorage.setItem('role', response.role);
        this.loggedInSubject.next(true);
        this.usernameSubject.next(response.username);
        this.roleSubject.next(response.role);
      })
    );
  }

  public isTokenExpired(): boolean {
    const token = localStorage.getItem('jwt');
    return !token || this.jwtHelper.isTokenExpired(token);
  }

  private refreshToken() {
    if (!this.isTokenExpired()) {
      this.apiService.get<LoginResponse>(ApiEndpoints.AUTH_REFRESH).subscribe(response => {
        localStorage.setItem('jwt', response.token);
      });
    }
  }
}
