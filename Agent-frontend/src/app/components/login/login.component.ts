import { Component, OnInit, Inject } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {
  cookieValue: string;
  errorMessage: string;
  tokenExpiredMessage: string;

  constructor(private cookieService: CookieService, private userService: UserService, private formBuilder: FormBuilder, private router: Router, @Inject(DOCUMENT) private document: any) { }
  
  loginForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    password: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
      ])]
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
    if (this.userService.getTokenExpiredMessage() != null) {
        this.tokenExpiredMessage = this.userService.getTokenExpiredMessage();
      }
  }

  login() {
    this.userService.loginUser(this.loginForm.value)
    .subscribe(res => {
      console.log(res);
      if (!!res['token']) {
          
          localStorage.setItem( 'token', res['token'] );
          console.log(localStorage);
          this.router.navigate(['/']);
      }
  
  });
  }
}
