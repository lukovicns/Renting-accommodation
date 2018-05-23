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
    return this.http.post(this.tokenUrl, user);
  }

  registerUser(user) {
    this.data = {
      'name': this.registerUser['name'],
      'surname': this.registerUser['surname'],
      'password': this.registerUser['password'],
      'city_id': 1,
      'street': this.registerUser['street'],
      'phone': this.registerUser['phone']
    }
    return this.http.post<User>(this.url + 'register/', this.data);
  }

  getCurrentUser() {
    const payload = decode(localStorage.getItem('token'));
    return payload ? payload : null;
  }
}
