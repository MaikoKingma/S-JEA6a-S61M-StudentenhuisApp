import { Component } from '@angular/core';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public thisState = State;

  title = 'the Studentenhuisapp';
  public currState = State.LOGIN;

  onStateChange($event) {
    this.currState = $event.state;
  }
}

export enum State {
  REGISTER = 0,
  LOGIN = 1,
  PROFILE = 2
}
