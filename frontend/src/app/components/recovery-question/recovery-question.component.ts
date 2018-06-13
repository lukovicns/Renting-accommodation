import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
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

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

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
          this.errorMessage = 'Email does\'t exist.';
        });
      }
    } else {
      this.router.navigate(['/']);
    }
  }

  sendMail() {
    this.data = {
      'email': this.userService.getEmail(),
      'answer': this.questionForm.value['answer']
    };
    this.userService.resetPassword(this.data)
    .subscribe(res => {
      console.log(res);
    }, err => {
      this.errorMessage = 'Answer is wrong!';
      console.log(err);
    });
    this.userService.setEmail('');
  }
}
