import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';
import { Agent } from '../models/Agent';
import { ResponseType } from '@angular/http';
import * as decode from 'jwt-decode';


@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:9001/api/users/';
  private tokenUrl: string = 'http://localhost:9001/token/';
  private email: string;

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
      'street': user.street,
      'phone': user.phone,
      'country': user.country,
      'city': user.city,
      'email': user.email,
      'question': user.question,
      'answer': user.answer,
      'agent': user.agent,
      'businessId': user.businessId
    };
    console.log(user);
    if (user.agent === false) {
      return this.http.post<User>(this.url + 'register/user', this.data);
    }
    return this.http.post<Agent>(this.url + 'register/agent', this.data);
  }

  getCurrentUser() {
    let payload;
    if (sessionStorage.length !== 0) {
      payload = decode(localStorage.getItem('token'));
      return payload;
    }
    return null;
  }

  changePassword(passwords) {
    const token = sessionStorage.getItem('token');
    console.log('token ' + token);
    this.data = {
      'oldPassword': passwords.oldPassword,
      'newPassword': passwords.newPassword,
      'token': token
    };
    return this.http.post(this.url + 'change', this.data);
  }

  resetPassword(sqDTO) {
    return this.http.post(this.url + 'resetPassword', sqDTO);
  }

  getQuestion(email) {
    return this.http.get(this.url + 'question/' + email.email);
  }

  getCountries() {
    return this.http.get(this.url + 'getCountries');
  }

  getCities(country) {
    return this.http.get(this.url + 'getCities/' + country);
  }

  setEmail(email) {
    this.email = email;
  }

  getEmail() {
    return this.email;
  }
}
