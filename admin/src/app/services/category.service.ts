import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AccommodationCategory } from '../models/AccommodationCategory';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url: string = 'https://localhost:8081/api/categories/';

  constructor(private http: HttpClient) { }
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    
  getCategories() {
    return this.http.get<AccommodationCategory[]>(this.url);
  }
  
  getCategory(categoryId) {
    return this.http.get<AccommodationCategory>(this.url + categoryId);
  }

  addCategory(data) {
    return this.http.post(this.url, data, { headers: this.headers });
  }

  editCategory(categoryId, data) {
    return this.http.put(this.url + categoryId, data, { headers: this.headers });
  }

  activateCategory(categoryId) {
    return this.http.put(this.url + categoryId + '/activate', null, { headers: this.headers });
  }

  deactivateCategory(categoryId) {
    return this.http.delete(this.url + categoryId, { headers: this.headers });
  }
}
