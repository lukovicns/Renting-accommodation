import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Admin } from '../models/Admin';
import * as decode from 'jwt-decode';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private data = {};
  private url: string = 'http://localhost:8081/api/admins/';
  private tokenUrl: string = 'http://localhost:8081/token/';
  private email: string;
  private tokenExpiredMessage: string;

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

  getTokenExpirationDate(token: string): Date {
    const decoded = decode(token);
    if (decoded.exp === undefined) return null;
    const date = new Date(0); 
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if(!token) token = localStorage.getItem('token');
    if(!token) return true;
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

}
