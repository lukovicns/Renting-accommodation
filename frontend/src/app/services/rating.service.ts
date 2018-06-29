import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class RatingService {

  private url: string = 'http://localhost:8081/api/ratings/';
  private headers = new HttpHeaders()
  .append('Content-Type', 'application/json')
  .append('Authorization', 'Bearer ' + localStorage.getItem('token'));

  constructor(private http: HttpClient) { }

  getAverageRatingForApartment(apartmentId) {
    return this.http.get(this.url + 'apartment/'+  apartmentId + '/average', { headers: this.headers });
  }

  getUserRatingForApartment(apartmentId) {
    return this.http.get(this.url + 'apartment/'+  apartmentId + '/user', { headers: this.headers });
  }

  rateApartment(data) {
    return this.http.post(this.url, data, { headers: this.headers });
  }
}
