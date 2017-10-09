import { Injectable } from '@angular/core';
import { HttpClient } from 'selenium-webdriver/http';
import { environment } from '../../environments/environment';

@Injectable()
export class ConfigService {
  private data = environment;

  getAccountApi(): string {
    return this.data.account_api;
  }

  getGroupApi(): string {
    return this.data.group_api;
  }
}
