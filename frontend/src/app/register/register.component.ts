import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../animations';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [fadeIn()]
})
export class RegisterComponent implements OnInit {

  constructor(private formBuilder: FormBuilder) { }

  registerForm = this.formBuilder.group({
    email: ['', Validators.required],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    street: ['', Validators.required],
    city: ['', Validators.required],
    password1: ['', Validators.required],
    password2: ['', Validators.required]
  });

  ngOnInit() {
  }

  register() {
    console.log(this.registerForm.value);
  }
}
