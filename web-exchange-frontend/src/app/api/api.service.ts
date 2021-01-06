import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  get<T>(path: string) {
    return this.http.get<T>(path);
  }

  post<T>(path: string, body: any) {
    return this.http.post<T>(path, body);
  }
}
