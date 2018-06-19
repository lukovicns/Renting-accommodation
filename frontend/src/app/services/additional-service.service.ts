import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdditionalService } from '../models/AdditionalService';

@Injectable()
export class AdditionalServiceService {

  private url: string = 'http://localhost:8081/api/additional-services/';

  constructor(private http: HttpClient) { }

  getAdditionalServices() {
    return this.http.get<AdditionalService[]>(this.url);
  }
}
