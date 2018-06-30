import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Comment } from '../models/Comment';

@Injectable()
export class CommentService {

  private url: string = 'https://localhost:8081/api/comments/';
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'))

  constructor(private http: HttpClient) { }

  getApartmentApprovedComments(apartmentId) {
    return this.http.get<Comment[]>(this.url + 'apartment/' + apartmentId + '/approved');
  }

  addComment(data) {
    return this.http.post<Comment>(this.url, data, { headers: this.headers });
  }
}
