import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AccommodationType } from '../models/AccommodationType';

@Injectable({
  providedIn: 'root'
})
export class TypeService {

  private url: string = 'http://localhost:8081/api/types/';

  constructor(private http: HttpClient) { }

  getTypes() {
    return this.http.get<AccommodationType[]>(this.url);
  }

  addType(data) {
    return this.http.post(this.url, data);
  }

  removeType(typeId) {
    return this.http.delete(this.url + typeId);
  }
}
