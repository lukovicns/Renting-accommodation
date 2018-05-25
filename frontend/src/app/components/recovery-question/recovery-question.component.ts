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

  constructor(private userService: UserService, private formBuilder: FormBuilder, private router: Router) { }

  data = {};
  question;

   questionForm = this.formBuilder.group({
    answer: ['', Validators.required]
  });

   ngOnInit() {
     this.question = this.getQuestion();
  }

  sendMail() {
      console.log(this.userService.getEmail()['email']);
      this.data = {
        'email': this.userService.getEmail()['email'],
        'answer': this.questionForm.value['answer']
      };
      this.userService.resetPassword(this.data)
      .subscribe();
      this.userService.setEmail('');
      console.log('mail ' + this.userService.getEmail()['email']);
    }

  getQuestion() {
    this.userService.getQuestion(this.userService.getEmail())
     .subscribe(res => {
        this.question = res['question'];
    });
  }

}
