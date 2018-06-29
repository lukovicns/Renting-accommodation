import { UserService } from '../../services/user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  animations: [fadeIn()]
})
export class RegisterComponent implements OnInit {

  errorMessage: String;
    countries;
    cities;
    selectedCountry;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  registerForm = this.formBuilder.group({
      email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    /*^[\w'\-,.][^0-9_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~<>;:[\]]{2,}$
*/    name: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[^±!@£$%^&*_+§¡€#¢§¶•ªº«\\/<>?:;|=.,]{1,20}$')
    ])],
    surname: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[^±!@£$%^&*_+§¡€#¢§¶•ªº«\\/<>?:;|=.,]{1,20}$')
    ])],
    phone: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[0-9]*$')
    ])],
//    /^\s*\S+(?:\s+\S+){2}/
    country: ['', Validators.required],
    city: ['', Validators.required],
    street: ['',  Validators.required,
    ],
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
    businessId: ['', Validators.required]
  });
      
  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
    this.getCountries();
  }
  
  getCountries(): void {
      this.userService.getCountries().subscribe(
              resultArray => {console.log(resultArray),
                this.countries = resultArray; });
  }
  
  onCountryChange(country) {
      this.userService.getCities(country).subscribe(resultArray => {console.log(resultArray),
                     this.cities = resultArray; });
  }

  register() {
      console.log('tttt');
    if (this.registerForm.value['password1'] != this.registerForm.value['password2']) {
        console.log('eeoe');
      this.errorMessage = 'Passwords don\t match!';
      
    } else {
        this.userService.registerUser(this.registerForm.value)
        .subscribe(
          res => console.log(res),
          err => console.log(err),
        );
        this.router.navigate(['/']);
      }
        
      /*this.userService.getCityByName(this.registerForm.value['city'])
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
      })*/
  }
  
}
