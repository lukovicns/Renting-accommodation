import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../models/Accommodation';
import { Apartment } from '../models/Apartment';

@Injectable()
export class ApartmentService {

  private url: string = 'http://localhost:8080/api/apartments/'

  constructor(private http: HttpClient) { }

  getApartments() {
    return this.http.get<Apartment[]>(this.url);
  }

  getApartment(apartmentId) {
    return this.http.get<Apartment>(this.url + apartmentId);
  }
}
