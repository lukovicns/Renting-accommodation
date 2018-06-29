import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';

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
  userBlocked: boolean;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

   questionForm = this.formBuilder.group({
    answer: ['', Validators.required]
  });

   ngOnInit() {
    if (!this.userService.getCurrentUser()) {
      if (!this.userService.emailEntered()) {
        this.router.navigate(['recovery']);
      } else {
        this.userService.getQuestion(this.userService.getEmail())
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

  // userExists(email) {
  //   this.userService.getUserByEmail(email)
  //   .subscribe(res => {
  //     console.log(res);
  //     return true;
  //   }, err => {
  //     this.errorMessage = err['error'];
  //     console.log(this.errorMessage);
  //     return false;
  //   });
  // }

  sendMail() {
    this.data = {
      'email': this.userService.getEmail(),
      'answer': this.questionForm.value['answer']
    }
    this.userService.resetPassword(this.data)
    .subscribe(res => {
      this.router.navigate(['login']);
      this.userService.setEmail('');
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
