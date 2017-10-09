import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

import { ConfigService } from './config.service';
import { Group } from '../models/group';

@Injectable()
export class GroupService {

  constructor(
    private http: HttpClient,
    private config: ConfigService) { }
    
    public create(group: Group): Observable<Group> {
      return this.http.post<Group>(this.config.getGroupApi(), group);
    }
}
