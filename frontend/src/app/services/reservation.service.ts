import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Reservation } from '../models/Reservation';
import { UserService } from './user.service';

@Injectable()
export class ReservationService {

  private url: string = 'https://localhost:8081/api/reservations/'
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + (localStorage.getItem('token')))

  private userReservations = [];

  constructor(private http: HttpClient, private userService: UserService) { }
  
  getReservations() {
    console.log(localStorage.getItem('token'));
    return this.http.get<Reservation[]>(this.url);
  }

  getUserReservations() {
    return this.http.get<Reservation[]>(this.url + 'user', { headers: this.headers });
  }

  getUserReservationByApartmentId(apartmentId) {
    return this.http.get<Reservation>(this.url + 'user/' + apartmentId, { headers: this.headers });
  }

  makeReservations(reservation) {
    return this.http.post(this.url, reservation, { headers: this.headers });
  }

  editReservation(reservationId, data) {
    return this.http.put(this.url + reservationId, data, { headers: this.headers });
  }

  cancelReservation(reservationId) {
    return this.http.delete<Reservation>(this.url + reservationId, { headers: this.headers });
  }
}
