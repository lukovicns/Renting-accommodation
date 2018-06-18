import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = 'http://localhost:8081/api/users/';

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get<User[]>(this.url);
  }

  getUser(userId) {
    return this.http.get<User[]>(this.url + userId);
  }
}
