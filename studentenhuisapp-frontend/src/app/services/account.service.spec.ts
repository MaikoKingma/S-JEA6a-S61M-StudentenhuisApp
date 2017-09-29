import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AccountService } from './account.service';
import { ConfigService } from './config.service';
import { MockConfigService } from './config.service.mock';
import { Account } from '../models/account';

describe('AccountService', () => {
  let httpMock: HttpTestingController;
  let accountService: AccountService;
  let configMock: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        { provide: ConfigService, useClass: MockConfigService },
        AccountService
      ]
    }).compileComponents();
    
    const testAccount = new Account('testUser', 'test@mail.nl');
    accountService = TestBed.get(AccountService);
    httpMock = TestBed.get(HttpTestingController);
    configMock = TestBed.get(ConfigService);
  });

  it('should be created', inject([AccountService], (service: AccountService) => {
    expect(service).toBeTruthy();
  }));
  it('should use the config mock', () => {
    expect(configMock.getAccountApi()).toBe('accounts/');
  });
  it('create() should return a Account', () => {
    accountService.create(this.testAccount).subscribe((account) => {
      expect(account.id).toBeGreaterThan(0);
      expect(account.fullName).toBe(this.testAccount.fullName);
      expect(account.mail).toBe(this.testAccount.mail);
    });

    const request = httpMock.expectOne(configMock.getAccountApi());
    request.flush({ id: 1, fullName: this.testAccount.fullName, mail: this.testAccount.mail });
    httpMock.verify();
  });
  fit('login() should return a Account', () => {
    accountService.login(this.testAccount.mail).subscribe((account) => {
      expect(account.id).toBeGreaterThan(0);
      expect(account.fullName).toBe(this.testAccount.fullName);
      expect(account.mail).toBe(this.testAccount.mail);
    });

    const request = httpMock.expectOne(configMock.getAccountApi());
    request.flush({ id: 1, fullName: this.testAccount.fullName, mail: this.testAccount.mail });
    httpMock.verify();
  });
});
