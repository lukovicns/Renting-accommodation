import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as decode from 'jwt-decode';

@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:8081/api/agents/';
  private tokenUrl: string = 'http://localhost:8081/token/';
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
//              'question': user.question,
//              'answer': user.answer,
//              'agent': user.agent,
              'businessId': user.businessId
            };
            console.log(user);
            
     return this.http.post(this.url + 'register', this.data);
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

  getCountries() {
      return this.http.get(this.url + 'getCountries');
  }

  getCities(country) {
      return this.http.get(this.url + 'getCities/' + country);
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
