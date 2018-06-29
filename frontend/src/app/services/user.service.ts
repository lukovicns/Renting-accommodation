import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';
import * as decode from 'jwt-decode';

@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:8081/api/users/';
  private tokenUrl: string = 'http://localhost:8081/token/';
  private email: string;
  private tokenExpiredMessage: string;

  constructor(private http: HttpClient) { }

  loginUser(user) {
    return this.http.post(this.url + 'login', user);
  }
  
  registerUser(user) {
    return this.http.post<User>(this.url + 'register', user);
  }

  getCurrentUser() {
    let payload = null;
    if (localStorage.getItem('token') != null) {
      payload = decode(localStorage.getItem('token'));
    }
    return payload;
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = decode(token);
    if (decoded.exp === undefined) return null;
    const date = new Date(0); 
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if(!token) token = localStorage.getItem('token');
    if (token == null) {
      return false;
    }
    // if(!token) return true;
    const date = this.getTokenExpirationDate(token);
    if(date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

  getTokenExpiredMessage() {
    return this.tokenExpiredMessage;
  }

  setTokenExpiredMessage(message) {
    this.tokenExpiredMessage = message;
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
    return this.http.post(this.url + 'reset', sqDTO);
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

  getUserByEmail(email) {
    return this.http.get(this.url + 'email/' + email);
  }
}
