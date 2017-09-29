import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MdInputModule, MdButtonModule, MdCardModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { LoginComponent } from './login.component';
import { ConfigService } from '../../services/config.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
        FormsModule,
        BrowserAnimationsModule,
        MdCardModule,
        MdButtonModule,
        MdInputModule,
        HttpClientTestingModule
      ],
      providers: [ ConfigService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
