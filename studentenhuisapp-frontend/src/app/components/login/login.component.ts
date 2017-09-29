import { Component, OnInit, EventEmitter, Output, NgZone } from '@angular/core';

import { AccountService } from '../../services/account.service';
import { State } from '../../app.component';

@Component({
  selector: 'app-login',
  providers: [AccountService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() onStateChange = new EventEmitter();

  public mail: string = '';
  public password: string = '';

  constructor(private accountService: AccountService,
    private _zone: NgZone
  ) { }

  ngOnInit() { }

  loginBtn(mail: string) {
    this.accountService.login(mail).subscribe(account => {
      this._zone.run(() => {
        this.onStateChange.emit({ state: State.PROFILE });
      });
    });
  }

  createAccountBtn() {
    this.onStateChange.emit({ state: State.REGISTER });
  }

  isValidForm(): boolean {
    if (this.mail === '' ||
      this.password === '') {
      return false;
    }
    return true;
  }
}
