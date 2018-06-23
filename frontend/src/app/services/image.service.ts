import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Image } from '../models/Image';

@Injectable()
export class ImageService {

  private url: string = 'http://localhost:8081/api/images/';

  constructor(private http: HttpClient) { }

  getFirstAccommodationImage(accommodationId) {
    return this.http.get<Image>(this.url + 'accommodation/' + accommodationId + '/first');
  }

  getOtherAccommodationImages(accommodationId) {
    return this.http.get<Image[]>(this.url + 'accommodation/' + accommodationId + '/others');
  }

  getFirstApartmentImage(apartmentId) {
    return this.http.get<Image>(this.url + 'apartment/' + apartmentId + '/first');
  }

  getOtherApartmentImages(apartmentId) {
    return this.http.get<Image[]>(this.url + 'apartment/' + apartmentId + '/others');
  }
}
