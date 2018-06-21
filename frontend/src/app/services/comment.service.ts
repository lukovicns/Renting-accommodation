import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Comment } from '../models/Comment';

@Injectable()
export class CommentService {

  private url: string = 'http://localhost:8081/api/comments/';

  constructor(private http: HttpClient) { }

  getApartmentComments(apartmentId) {
    return this.http.get<Comment[]>(this.url + 'apartment/' + apartmentId);
  }
}
