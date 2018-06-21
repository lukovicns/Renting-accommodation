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

  constructor(private cookieService: CookieService, private userService: UserService, private formBuilder: FormBuilder, private router: Router, @Inject(DOCUMENT) private document: any) { }
  
  loginForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    password: ['', Validators.required]
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
  }

  login() {
    this.userService.loginUser(this.loginForm.value)
    .subscribe(res => {
      console.log(res);
      if (!!res['token']) {
          this.cookieService.set( 'token', res['token'] );
          this.cookieValue = this.cookieService.get('token');
          console.log(this.cookieValue);
        //sessionStorage.setItem('token', res['token']);
        //const token = storage.get('token');
        //console.log(token);
        //const user = this.userService.getCurrentUser();
        //if (user['role'] === 'AGENT') {
          this.document.location.href = 'http://localhost:4201';
       /* }
        else if (user['role'] === 'USER') {
          this.router.navigate(['/']);
        }*/
    }
  });
  }
}
