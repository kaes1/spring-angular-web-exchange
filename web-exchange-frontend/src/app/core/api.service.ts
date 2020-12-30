import {Injectable} from '@angular/core';
import {CoreModule} from './core.module';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: CoreModule
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
