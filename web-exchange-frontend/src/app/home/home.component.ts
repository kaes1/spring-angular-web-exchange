import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  testResponse = '';

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.fetchTestString();
  }

  private fetchTestString() {
    this.http.get('api/test/5', {responseType: 'text'}).subscribe(response => {
      console.log(response);
      this.testResponse = response;
    });
    return 'Response';
  }

}
