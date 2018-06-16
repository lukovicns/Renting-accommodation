import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Reservation } from '../models/Reservation';

@Injectable()
export class ReservationService {

  private url: string = 'http://localhost:8080/api/reservations/'
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'));
  
  constructor(private http: HttpClient) { }
  
  getReservations() {
    return this.http.get<Reservation[]>(this.url);
  }

  getUserReservations() {
    return this.http.get<Reservation[]>(this.url + 'user', { headers: this.headers });
  }

  makeReservations(reservation) {
    return this.http.post(this.url, reservation, { headers: this.headers });
  }

  removeReservation(reservationId) {
    return this.http.delete<Reservation>(this.url + reservationId, { headers: this.headers });
  }
}