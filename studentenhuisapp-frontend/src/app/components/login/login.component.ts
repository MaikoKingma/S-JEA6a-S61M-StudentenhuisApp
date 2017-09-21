import { Component, OnInit, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() onRegisterAccount = new EventEmitter();

  public mail: string = '';
  public password: string = '';

  constructor() { }

  ngOnInit() { }

  loginBtn(mail: string) {

  }

  createAccountBtn() {
    this.onRegisterAccount.emit();
  }

  isValidForm(): boolean {
    if (this.mail === '' ||
      this.password === '') {
      return false;
    }
    return true;
  }
}
