import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
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
    oldPassword: ['', Validators.required],
    newPassword: ['', Validators.compose([
      Validators.minLength(8),
      Validators.required
    ])]
  });

  changePassword() {
    this.userService.changePassword(this.changePassForm.value)
    .subscribe(res => {
      // console.log(res);
      this.router.navigate(['/']);
    },
    err => {
      this.errorMessage = err['error'];
      console.log(err);
    });
  }

}
