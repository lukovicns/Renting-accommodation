import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';
import { ResponseType } from '@angular/http';
import * as decode from 'jwt-decode';

@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:8080/api/users/';
  private tokenUrl: string = 'http://localhost:8080/token/';
  private email: string;

  constructor(private http: HttpClient) { }

  loginUser(user) {
    return this.http.post(this.url + 'login', user);
  }
  
  registerUser(user) {
    this.data = {
      'name': user.name,
      'surname': user.surname,
      'password': user.password1,
      'street': user.street,
      'phone': user.phone,
      'email': user.email,
      'question': user.question,
      'answer': user.answer
    };
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
    return this.http.get(this.url + 'question/' + email);
  }

  setEmail(email) {
    this.email = email;
  }

  getEmail() {
    return this.email;
  }

  emailEntered() {
    return this.email != null;
  }

  getUsers() {
    return this.http.get(this.url);
  }
}
