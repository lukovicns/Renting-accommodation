import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApartmentAdditionalService } from '../models/ApartmentAdditionalService';

@Injectable()
export class ApartmentAdditionalServiceService {

  private url: string = 'http://localhost:8081/api/apartment-additional-services/';

  constructor(private http: HttpClient) { }

  getApartmentAdditionalServices(apartmentId) {
    return this.http.get<ApartmentAdditionalService[]>(this.url + apartmentId);
  }
}
