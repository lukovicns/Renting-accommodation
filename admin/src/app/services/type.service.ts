import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AccommodationType } from '../models/AccommodationType';

@Injectable({
  providedIn: 'root'
})
export class TypeService {

  private url: string = 'http://localhost:8081/api/types/';

  constructor(private http: HttpClient) { }
  private headers = new HttpHeaders()
  .append('Content-Type', 'application/json')
  .append('Authorization', 'Bearer ' + localStorage.getItem('token'))

  getTypes() {
    return this.http.get<AccommodationType[]>(this.url);
  }

  getType(typeId) {
    return this.http.get<AccommodationType>(this.url + typeId);
  }

  addType(data) {
    return this.http.post(this.url, data, { headers: this.headers });
  }

  editType(typeId, data) {
    return this.http.put(this.url + typeId, data, { headers: this.headers });
  }

  activateType(typeId) {
    return this.http.put(this.url + typeId + '/activate', null, { headers: this.headers });
  }

  deactivateType(typeId) {
    return this.http.delete(this.url + typeId, { headers: this.headers });
  }
}
