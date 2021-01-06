import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {LoginRequest} from '../model/login-request.model';
import {LoginResponse} from '../model/login-response.model';
import {Observable, of, ReplaySubject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import {ApiEndpoints} from '../api/api-endpoints';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedInSubject = new ReplaySubject<boolean>(1);
  private usernameSubject = new ReplaySubject<string>(1);

  constructor(private apiService: ApiService,
              private jwtHelper: JwtHelperService) {
    const token = localStorage.getItem('jwt') || undefined;
    const username = localStorage.getItem('username') || undefined;
    const isLoggedIn = (token !== undefined && !this.jwtHelper.isTokenExpired(token));
    this.loggedInSubject.next(isLoggedIn);
    this.usernameSubject.next(isLoggedIn ? username : undefined);
  }

  public getLoggedIn(): Observable<boolean> {
    return this.loggedInSubject.asObservable();
  }

  public getUsername(): Observable<string> {
    return this.usernameSubject.asObservable();
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

    return this.apiService.post<LoginResponse>(ApiEndpoints.LOGIN, loginRequest).pipe(
      tap(response => {
        console.log('Got response in AuthService!');
        console.log(response);
        localStorage.setItem('jwt', response.token);
        localStorage.setItem('username', response.username);
        this.loggedInSubject.next(true);
        this.usernameSubject.next(response.username);
      })
    );
  }

}
