import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AccommodationType } from '../models/AccommodationType';

@Injectable()
export class AccommodationTypeService {

  private url: string = 'http://localhost:8081/api/types/';

  constructor(private http: HttpClient) { }

  getTypes() {
    return this.http.get<AccommodationType[]>(this.url);
  }
}
