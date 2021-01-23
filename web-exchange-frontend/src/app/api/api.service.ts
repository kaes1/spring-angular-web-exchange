import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  get<T>(path: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(path, {params}).pipe(
      catchError(err => {
        throw err.error;
      })
    );
  }

  post<T>(path: string, body?: any, params?: HttpParams): Observable<T> {
    return this.http.post<T>(path, body, {params}).pipe(
      catchError(err => {
        throw err.error;
      })
    );
  }
}
