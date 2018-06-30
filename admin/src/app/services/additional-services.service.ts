import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AdditionalService } from '../models/AdditionalService';

@Injectable({
  providedIn: 'root'
})
export class AdditionalServicesService {

  private url: string = 'https://localhost:8081/api/additional-services/';
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    
  constructor(private http: HttpClient) { }

  getAdditionalServices() {
    return this.http.get<AdditionalService[]>(this.url);
  }

  getAdditionalService(additionalServiceId) {
    return this.http.get<AdditionalService>(this.url + additionalServiceId);
  }

  addAdditionalService(data) {
    return this.http.post(this.url, data, { headers: this.headers });
  }

  editAdditionalService(additionalServiceId, data) {
    return this.http.put(this.url + additionalServiceId, data, { headers: this.headers });
  }

  deleteAdditionalService(additionalServiceId) {
    return this.http.delete(this.url + additionalServiceId, { headers: this.headers });
  }
}
