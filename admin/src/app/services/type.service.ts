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

  getType(typeId) {
    return this.http.get<AccommodationType>(this.url + typeId);
  }

  addType(data) {
    return this.http.post(this.url, data);
  }

  editType(typeId, data) {
    return this.http.put(this.url + typeId, data);
  }

  activateType(typeId) {
    return this.http.put(this.url + typeId + '/activate', null);
  }

  deactivateType(typeId) {
    return this.http.delete(this.url + typeId);
  }
}
