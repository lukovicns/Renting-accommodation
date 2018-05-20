import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';

@Injectable()
export class UserService {

  private data = {};
  private url: string = 'http://localhost:8080/api/users/';

  constructor(private http: HttpClient) { }

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
}
