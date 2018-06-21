import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Comment } from '../models/Comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private url: string = 'http://localhost:8081/api/comments/';
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'))

  constructor(private http: HttpClient) { }

  getWaitingComments() {
    return this.http.get<Comment[]>(this.url + 'waiting');
  }

  approveComment(commentId) {
    return this.http.put<Comment>(this.url + commentId + '/approve', null, { headers: this.headers });
  }

  declineComment(commentId) {
    return this.http.delete<Comment>(this.url + commentId, { headers: this.headers });
  }
}
