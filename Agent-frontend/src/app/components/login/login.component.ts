import { Component, OnInit, Inject } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [fadeIn()]
})
export class LoginComponent implements OnInit {
  cookieValue: string;
  errorMessage: string;
  tokenExpiredMessage: string;

  constructor(private cookieService: CookieService, private userService: UserService, private formBuilder: FormBuilder, private router: Router, @Inject(DOCUMENT) private document: any) { }
  
  loginForm = this.formBuilder.group({
    email: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$')
    ])],
    password: ['', Validators.compose([
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$')
      ])]
  });

  ngOnInit() {
    if (this.userService.getCurrentUser()) {
      this.router.navigate(['/']);
    }
    if (this.userService.getTokenExpiredMessage() != null) {
        this.tokenExpiredMessage = this.userService.getTokenExpiredMessage();
      }
  }

  login() {
      console.log(this.loginForm.value);
    this.userService.loginUser(this.loginForm.value).subscribe(res => {
        console.log(res);
        if (!!res['token']) {
            
            localStorage.setItem( 'token', res['token'] );
//            localStorage = localStorage.get('token');
            console.log(localStorage);
            this.router.navigate(['/']);
          //sessionStorage.setItem('token', res['token']);
          //const token = storage.get('token');
          //console.log(token);
          //const user = this.userService.getCurrentUser();
          //if (user['role'] === 'AGENT') {
//            this.document.location.href = 'http://localhost:4201';
         /* }
          else if (user['role'] === 'USER') {
            this.router.navigate(['/']);
          }*/
    }}, err => {console.error(err); 
            if(err.status == 404)
            {
                swal(
                'You have to register first!'
              ).then((result) => this.router.navigate(['/register']))
            
            }else if(err.status == 403)
            {
                swal(
                        'Your status is not approved'
                      )
            }else if(err.status == 406)
            {
                swal(
                        'Inavlid username or password'
                      )
            }
        });

    /*.subscribe(res => {
=======
    this.userService.loginUser(this.loginForm.value)
    .subscribe(res => {
>>>>>>> c434c8abd3691dd00f7c0468fc6778da6c401a6e
      console.log(res);
      if (!!res['token']) {
          
          localStorage.setItem( 'token', res['token'] );
          console.log(localStorage);
<<<<<<< HEAD
          this.router.navigate(['/']);*/
        //sessionStorage.setItem('token', res['token']);
        //const token = storage.get('token');
        //console.log(token);
        //const user = this.userService.getCurrentUser();
        //if (user['role'] === 'AGENT') {
//          this.document.location.href = 'http://localhost:4201';
       /* }
        else if (user['role'] === 'USER') {
          this.router.navigate(['/']);
        }*/
          this.router.navigate(['/']);
      }
  
}
