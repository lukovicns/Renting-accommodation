import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
//import { Reservation } from '../models/Reservation';

@Injectable()
export class ReservationService {

    private url = 'http://localhost:9000/restIntercepter';
 /* private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'));*/
  
  private userReservations = [];

  constructor(private http: HttpClient) { }
  
  getReservations() {
      console.log('tu');
    return this.http.get(this.url + '/getReservations');
  }

  confirmReservation(id) {
      return this.http.get(this.url + '/confirmReservation/' + id);
  }
  
  
}
