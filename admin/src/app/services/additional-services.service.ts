import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdditionalService } from '../models/AdditionalService';

@Injectable({
  providedIn: 'root'
})
export class AdditionalServicesService {

  private url: string = 'http://localhost:8081/api/additional-services/';

  constructor(private http: HttpClient) { }

  getAdditionalServices() {
    return this.http.get<AdditionalService[]>(this.url);
  }

  getAdditionalService(additionalServiceId) {
    return this.http.get<AdditionalService>(this.url + additionalServiceId);
  }

  addAdditionalService(data) {
    return this.http.post(this.url, data);
  }

  editAdditionalService(additionalServiceId, data) {
    return this.http.put(this.url + additionalServiceId, data);
  }

  deleteAdditionalService(additionalServiceId) {
    return this.http.delete(this.url + additionalServiceId);
  }
}
