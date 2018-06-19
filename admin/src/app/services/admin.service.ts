import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../models/Admin';
import * as decode from 'jwt-decode';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private url: string = 'http://localhost:8081/api/admins/';

  constructor(private http: HttpClient) { }

  getCurrentAdmin() {
    let payload = null;
    if (localStorage.getItem('token') != null) {
      payload = decode(localStorage.getItem('token'));
    }
    return payload;
  }

  loginAdmin(admin) {
    return this.http.post<Admin>(this.url + 'login', admin);
  }
  
  blockUser(userId) {
    return this.http.put<User>(this.url + 'block-user/' + userId, "B");
  }

  activateUser(userId) {
    return this.http.put<User>(this.url + 'activate-user/' + userId, null);
  }

  deleteUser(userId) {
    return this.http.delete<User>(this.url + 'delete-user/' + userId);
  }
}
