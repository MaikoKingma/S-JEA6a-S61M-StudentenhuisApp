import { TestBed, async } from '@angular/core/testing';
import { MdCardModule, MdButtonModule, MdInputModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';


import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ConfigService } from './services/config.service';
import { HomeComponent } from './components/home/home.component';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        LoginComponent,
        RegisterComponent,
        HomeComponent
      ],
      imports: [
        FormsModule,
        BrowserAnimationsModule,
        MdCardModule,
        MdButtonModule,
        MdInputModule,
        HttpClientTestingModule
      ],
      providers: [ ConfigService ]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`should have as title 'the Studentenhuisapp'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('the Studentenhuisapp');
  }));

  it('should render title in a h1 tag', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to the Studentenhuisapp!');
  }));
});
