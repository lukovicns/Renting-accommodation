import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AccommodationCategory } from '../models/AccommodationCategory';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url: string = 'http://localhost:8081/api/categories/';

  constructor(private http: HttpClient) { }

  getCategories() {
    return this.http.get<AccommodationCategory[]>(this.url);
  }
  
  getCategory(categoryId) {
    return this.http.get<AccommodationCategory>(this.url + categoryId);
  }

  addCategory(data) {
    return this.http.post(this.url, data);
  }

  editCategory(categoryId, data) {
    return this.http.put(this.url + categoryId, data);
  }

  activateCategory(categoryId) {
    return this.http.put(this.url + categoryId + '/activate', null);
  }

  deactivateCategory(categoryId) {
    return this.http.delete(this.url + categoryId);
  }
}
