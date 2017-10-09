import { TestBed, inject } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpTestingController } from '@angular/common/http/testing';

import { GroupService } from './group.service';
import { ConfigService } from './config.service';
import { MockConfigService } from './config.service.mock';
import { Group } from '../models/group';

describe('GroupService', () => {
  let httpMock: HttpTestingController;
  let groupService: GroupService;
  let configMock: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        { provide: ConfigService, useClass: MockConfigService },
        GroupService
      ]
    }).compileComponents();

    groupService = TestBed.get(GroupService);
    httpMock = TestBed.get(HttpTestingController);
    configMock = TestBed.get(ConfigService);
  });

  it('should be created', inject([GroupService], (service: GroupService) => {
    expect(service).toBeTruthy();
  }));
  it('should use the config mock', () => {
    expect(configMock.getAccountApi()).toBe('accounts/');
  });
  it('create() should return a Account', () => {
    const testGroup = new Group('testGroup');

    groupService.create(testGroup).subscribe((group) => {
      expect(group.id).toBeGreaterThan(0);
      expect(group.name).toBe(testGroup.name);
    });

    const request = httpMock.expectOne(configMock.getAccountApi());
    request.flush({ id: 1, name: testGroup.name });
    httpMock.verify();
  });
});
