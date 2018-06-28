import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';
import { CityService } from '../../services/city.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [fadeIn()]
})
export class RegisterComponent implements OnInit {

  errorMessage: String;
  private cities: any = [];

  constructor(
    private userService: UserService,
    private cityService: CityService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  registerForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    name: ['', Validators.required],
    surname: ['', Validators.required],
    phone: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[0-9]*$')
    ])],
    street: ['', Validators.required],
    city: ['', Validators.required],
    password1: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
    ])],
    password2: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
    ])],
    question: ['', Validators.required],
    answer: ['', Validators.required],
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    } else {
      this.cityService.getCities()
      .subscribe(res => {
        this.cities = res;
        if (this.cities && this.cities.length > 0) {
          this.registerForm.controls['city'].setValue(this.cities[0].id);
        }
      })
    }
  }

  register() {
    if (this.registerForm.value['password1'] != this.registerForm.value['password2']) {
      this.errorMessage = 'Passwords don\t match!';
    } else {
      this.cityService.getCity(this.registerForm.value['city'])
      .subscribe(res => {
        const data = {
          'name': this.registerForm.value.name,
          'surname': this.registerForm.value.surname,
          'password': this.registerForm.value.password1,
          'email': this.registerForm.value.email,
          'city': res,
          'street': this.registerForm.value.street,
          'phone': this.registerForm.value.phone,
          'question': this.registerForm.value.question,
          'answer': this.registerForm.value.answer
        };
        this.userService.registerUser(data)
        .subscribe(resp => {
            this.router.navigate(['login']);
          }, err => {
            this.errorMessage = err['error'];
        });
      }, err => {
        this.errorMessage = err['error'];
      })
    }
  }
}
