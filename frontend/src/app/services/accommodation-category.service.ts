import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AccommodationCategory } from '../models/AccommodationCategory';

@Injectable()
export class AccommodationCategoryService {

  private url: string = 'http://localhost:8081/api/categories/';

  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get<AccommodationCategory[]>(this.url);
  }
}
