import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../models/Accommodation';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AccommodationService {

  private url: string = 'http://localhost:8080/api/accommodations/';

  constructor(private http: HttpClient) { }

  getAccommodations() {
    return this.http.get<Accommodation[]>(this.url);
  }

  getAccommodation(id): Observable<Accommodation> {
    return this.http.get<Accommodation>(this.url + id);
  }
}
