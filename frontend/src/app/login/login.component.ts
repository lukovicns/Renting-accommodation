import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../animations';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { }

  loginForm = this.formBuilder.group({
    email: ['', Validators.required],
    password: ['', Validators.required]
  });

  ngOnInit() {
  }

  login() {
  }
}
