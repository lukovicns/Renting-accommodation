import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {

  errorMessage: string;
  tokenExpiredMessage: string;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

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
    this.tokenExpiredMessage = this.userService.getTokenExpiredMessage();
  }

  login() {
    this.userService.loginUser(this.loginForm.value)
    .subscribe(res => {
      if (res['token'] != null) {
        localStorage.setItem('token', res['token']);
        window.location.reload();
      }
    }, err => {
      this.errorMessage = err['error'];
      this.loginForm.reset();
    });
  }
}
