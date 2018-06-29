import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-recovery-question',
  templateUrl: './recovery-question.component.html',
  styleUrls: ['./recovery-question.component.css'],
  animations: [fadeIn()]
})
export class RecoveryQuestionComponent implements OnInit {

  data = {};
  question;
  errorMessage: String;
  adminBlocked: boolean;

  constructor(
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  questionForm = this.formBuilder.group({
    answer: ['', Validators.required]
  });

  ngOnInit() {
    if (!this.adminService.getCurrentAdmin()) {
      if (!this.adminService.emailEntered()) {
        this.router.navigate(['recovery']);
      } else {
        this.adminService.getQuestion(this.adminService.getEmail())
        .subscribe(res => {
          this.question = res['question'];
        }, err => {
          this.errorMessage = 'Email doesn\'t exist.';
        });
      }
    } else {
      this.router.navigate(['/']);
    }
  }

  sendMail() {
    this.data = {
      'email': this.adminService.getEmail(),
      'answer': this.questionForm.value['answer']
    }
    this.adminService.resetPassword(this.data)
    .subscribe(res => {
      this.router.navigate(['login']);
      this.adminService.setEmail('');
    }, err => {
      if (err['error'].status != null && err['error'].status === 'BLOCKED') {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      } else {
        this.errorMessage = err['error'];
      }
      this.questionForm.reset();
    });
  }
}
