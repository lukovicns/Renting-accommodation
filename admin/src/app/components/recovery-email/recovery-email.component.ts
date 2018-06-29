import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-recovery-email',
  templateUrl: './recovery-email.component.html',
  styleUrls: ['./recovery-email.component.css'],
  animations: [fadeIn()]
})
export class RecoveryEmailComponent implements OnInit {

  private errorMessage: string;

  constructor(
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  recoveryForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])]
  });

  ngOnInit() {
    if (this.adminService.getCurrentAdmin()) {
      this.router.navigate(['/']);
    }
  }

  next() {
    this.adminService.setEmail(this.recoveryForm.value['email']);
    this.adminService.getAdminByEmail(this.adminService.getEmail())
    .subscribe(res => {
      this.router.navigate(['question']);
    }, err => {
      this.errorMessage = err['error'];
      this.recoveryForm.reset();
    })
  }
}
