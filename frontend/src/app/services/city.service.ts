import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { City } from '../models/City';

@Injectable()
export class CityService {

  private url: string = 'http://localhost:8081/api/cities/';

  constructor(private http: HttpClient) { }

  getCities() {
    return this.http.get<City[]>(this.url);
  }

  getCity(cityId) {
    return this.http.get<City>(this.url + cityId);
  }

  getCityByName(cityName) {
    return this.http.get<City>(this.url + 'name/' + cityName);
  }
}
