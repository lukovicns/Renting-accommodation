import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Apartment } from '../models/Apartment';

@Injectable()
export class ApartmentService {

  private url: string = 'http://localhost:8081/api/apartments/';
  private headers = new HttpHeaders()
  .append('Content-Type', 'application/json')
  .append('Authorization', 'Bearer ' + localStorage.getItem('token'));

  constructor(private http: HttpClient) { }

  getApartments(accommodationId) {
    return this.http.get<Apartment[]>(this.url + 'accommodation/' + accommodationId);
  }

  getApartment(apartmentId) {
    return this.http.get<Apartment>(this.url + apartmentId);
  }

  getApartmentsByQueryParams(city, persons, startDate, endDate) {
    return this.http.get<Apartment[]>(this.url + 'search', { params: {
      'city': city,
      'persons': persons,
      'startDate': startDate,
      'endDate': endDate
      }, headers: this.headers
    });
  }
}
