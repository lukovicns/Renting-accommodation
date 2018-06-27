import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  animations: [fadeIn()]
})
export class ProfileComponent {

  errorMessage: String;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

  changePassForm = this.formBuilder.group({
    oldPassword: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
    ])],
    newPassword: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
    ])]
  });

  changePassword() {
    this.userService.changePassword(this.changePassForm.value)
    .subscribe(res => {
      localStorage.removeItem('token');
      this.router.navigate(['/login']);
    },
    err => {
      if (err['error'].status != null && err['error'].status === 'BLOCKED') {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      } else {
        this.errorMessage = err['error'];
      }
      this.changePassForm.reset();
    });
  }
}
