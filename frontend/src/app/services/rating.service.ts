import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RatingService {

  private url: string = 'http://localhost:8081/api/ratings/';

  constructor(private http: HttpClient) { }

  getAverageRatingForApartment(apartmentId) {
    return this.http.get(this.url + 'apartment/'+  apartmentId + '/average')
  }
}
