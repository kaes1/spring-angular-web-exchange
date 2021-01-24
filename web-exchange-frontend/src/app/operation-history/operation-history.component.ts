import { Component, OnInit } from '@angular/core';
import {ApiService} from '../api/api.service';
import {ApiEndpoints} from '../api/api-endpoints';
import {HttpParams} from '@angular/common/http';
import {OperationHistory} from '../model/operation-history.model';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-operation-history',
  templateUrl: './operation-history.component.html',
  styleUrls: ['./operation-history.component.scss'],
  providers: [DatePipe]
})
export class OperationHistoryComponent implements OnInit {

  fromDate = '';
  toDate = '';

  operationHistoryList: OperationHistory[] = [];

  constructor(private apiService: ApiService,
              private datePipe: DatePipe) { }

  ngOnInit(): void {
    const currentDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd') as string;
    this.fromDate = currentDate;
    this.toDate = currentDate;
    this.fetchOperationHistory();
  }

  fetchOperationHistory() {
    const httpParams = new HttpParams()
      .set('from', this.fromDate)
      .set('to', this.toDate);
    this.apiService.get<OperationHistory[]>(ApiEndpoints.OPERATIONS_HISTORY, httpParams).subscribe((response: OperationHistory[]) => {
      console.log(response);
      this.operationHistoryList = response;
    });
  }
}
