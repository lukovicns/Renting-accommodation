import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Apartment } from '../models/Apartment';

@Injectable()
export class ApartmentService {

  private url: string = 'http://localhost:8081/api/apartments/'

  constructor(private http: HttpClient) { }

  getApartments() {
    return this.http.get<Apartment[]>(this.url);
  }

  getApartment(apartmentId) {
    return this.http.get<Apartment>(this.url + apartmentId);
  }

  getApartmentsByQueryParams(accommodationId, city, persons, startDate, endDate) {
    throw new Error("Method not implemented.");
  }
}
