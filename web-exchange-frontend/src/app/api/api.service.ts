import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  get<T>(path: string) {
    return this.http.get<T>(path).pipe(
      catchError(err => {
        throw err.error;
      })
    );
  }

  post<T>(path: string, body: any) {
    return this.http.post<T>(path, body).pipe(
      catchError(err => {
        throw err.error;
      })
    );
  }
}
