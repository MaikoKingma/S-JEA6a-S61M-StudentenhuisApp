import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ConfigService } from './config.service';
import { Account } from '../models/account';
import 'rxjs/add/operator/map';

@Injectable()
export class AccountService {

  constructor(
    private http: HttpClient,
    private config: ConfigService
  ) { }
  
  public create(account: Account): Observable<Account> {
    return this.http.post<Account>(this.config.getAccountApi(), account);
  }

  public login() {
    let responseStatus;
    let body = this.http.get(this.config.getAccountApi(), {observe: 'response'})
      .map(response => responseStatus = response.status);
    console.log(body);
    console.log(responseStatus);
  }
}
