import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';

@Injectable()
export class MockConfigService extends ConfigService {

    getAccountApi(): string {
        return 'accounts/'
      }
}
