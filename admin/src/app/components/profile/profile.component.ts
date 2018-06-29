import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  animations: [fadeIn()]
})
export class ProfileComponent implements OnInit {

  private errorMessage: string;

  constructor(
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

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

  ngOnInit() {
  }

  changePassword() {
    this.adminService.changePassword(this.changePassForm.value)
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
