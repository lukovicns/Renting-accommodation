import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {

  errorMessage: String;
  tokenExpiredMessage: string;

  constructor(
    private adminService: AdminService,
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
    if (this.adminService.getCurrentAdmin()) {
      this.router.navigate(['/']);
    }
    if (this.adminService.getTokenExpiredMessage() != null) {
      this.tokenExpiredMessage = this.adminService.getTokenExpiredMessage();
    }
  };

  login() {
    this.adminService.loginAdmin(this.loginForm.value)
    .subscribe(res => {
      if (!!res['token']) {
        localStorage.setItem('token', res['token']);
        window.location.reload();
      }
    }, err => {
      this.errorMessage = err['error'];
      this.loginForm.reset();
    })
  }
}
