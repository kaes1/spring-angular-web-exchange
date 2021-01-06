import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {LoginRequest} from '../model/login-request.model';
import {LoginResponse} from '../model/login-response.model';
import {Observable, of, ReplaySubject} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedInSubject = new ReplaySubject<boolean>(1);

  constructor(private apiService: ApiService,
              private jwtHelper: JwtHelperService) {
    const token = localStorage.getItem('jwt');
    const isLoggedIn = (token !== null && !this.jwtHelper.isTokenExpired(token));
    this.loggedInSubject.next(isLoggedIn);
  }

  public getLoggedIn(): Observable<boolean> {
    return this.loggedInSubject.asObservable();
  }

  public logout() {
    localStorage.removeItem('jwt');
    this.loggedInSubject.next(false);
  }

  public login(login: string, password: string): Observable<any> {

    const loginRequest: LoginRequest = {
      login, password
    };

    return this.apiService.post<LoginResponse>('api/login', loginRequest).pipe(
      tap(response => {
        console.log('Got response in AuthService!');
        console.log(response);
        localStorage.setItem('jwt', response.token);
        localStorage.setItem('username', response.username);
        this.loggedInSubject.next(true);
      }),
      catchError(err => {
        console.log('Got err!');
        console.log(err);
        throw new Error('Some error');
        // return of('Some Error here?');
      })
    );
  }

}
