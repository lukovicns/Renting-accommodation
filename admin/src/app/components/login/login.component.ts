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
      Validators.minLength(8)
    ])]
  });

  ngOnInit() {
    if (this.adminService.getCurrentAdmin()) {
      this.router.navigate(['/']);
    }
  }

  login() {
    this.adminService.loginAdmin(this.loginForm.value)
    .subscribe(res => {
      if (!!res['token']) {
        localStorage.setItem('token', res['token']);
        this.router.navigate(['/']);
      }
    }, err => {
      this.errorMessage = err.error;
    })
  }
}
