import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';
import * as decode from 'jwt-decode';

@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:8080/api/users/';
  private tokenUrl: string = 'http://localhost:8080/token/';

  constructor(private http: HttpClient) { }

  loginUser(user) {
    console.log(user);
    return this.http.post(this.url + 'login', user);
  }
  registerUser(user) {
    this.data = {
      'name': user.name,
      'surname': user.surname,
      'password': user.password1,
//      'city': user.city,
      'street': user.street,
      'phone': user.phone,
      'email': user.email
    };
    console.log(user);
    return this.http.post<User>(this.url + 'register', this.data);
  }

  getCurrentUser() {
    let payload;
    if (localStorage.length !== 0) {
      payload = decode(localStorage.getItem('token'));
      return payload;
    }
    return null;
  }

  changePassword(passwords) {
    const token = localStorage.getItem('token');
    console.log('token ' + token);
    this.data = {
      'oldPassword': passwords.oldPassword,
      'newPassword': passwords.newPassword,
      'token': token
    };
    return this.http.post(this.url + 'change', this.data);
  }

  resetPassword(email) {
    console.log(email);
   /* this.data = {
      'email': email
    };*/
    return this.http.post(this.url + 'resetPassword', email);
  }
}
