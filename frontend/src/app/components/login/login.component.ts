import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

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
        localStorage.setItem('token', res['token']);
        this.router.navigate(['/']);
      }
    });
  }

}
