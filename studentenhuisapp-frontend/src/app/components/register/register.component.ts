import { Component, OnInit, EventEmitter, Output, NgZone } from '@angular/core';

import { Account } from '../../models/Account';
import { AccountService } from '../../services/account.service';
import { State } from '../../app.component';

@Component({
  selector: 'app-register',
  providers: [AccountService],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  @Output() onStateChange = new EventEmitter();

  public newAccount: Account = new Account();

  constructor(private accountService: AccountService,
    private _zone: NgZone
  ) { }

  ngOnInit() { }

  cancelBtn() {
    this.newAccount = new Account();
    this.onStateChange.emit({ state: State.LOGIN });
  }

  createAccountBtn(account: Account) {
    this.accountService.create(account).subscribe(account => {
      this._zone.run(() => {
        this.newAccount = new Account();
        this.onStateChange.emit({ state: State.LOGIN });
      });
    });
  }
  
  isValidForm(): boolean {
    if (this.newAccount.mail === '' ||
      this.newAccount.fullName === '') {
      return false;
    }
    return true;
  }
}
