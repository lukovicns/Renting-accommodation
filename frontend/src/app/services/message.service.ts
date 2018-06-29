import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Message } from '../models/Message';

@Injectable()
export class MessageService {

  private url: string = 'http://localhost:8081/api/messages/';
  private headers = new HttpHeaders()
    .append('Content-Type', 'application/json')
    .append('Authorization', 'Bearer ' + localStorage.getItem('token'))

  constructor(private http: HttpClient) { }

  sendMessage(data) {
    return this.http.post(this.url + 'user-to-agent', data, { headers: this.headers });
  }

  getSentMessages(userId) {
    return this.http.get<Message[]>(this.url + 'user/' + userId + '/sent', { headers: this.headers });
  }

  getReceivedMessages(userId) {
    return this.http.get<Message[]>(this.url + 'user/' + userId + '/received', { headers: this.headers });
  }

  getSentMessage(userId, messageId) {
    return this.http.get<Message>(this.url + 'user/' + userId + '/sent/' + messageId, { headers: this.headers });
  }

  getReceivedMessage(userId, messageId) {
    return this.http.get<Message>(this.url + 'user/' + userId + '/received/' + messageId, { headers: this.headers });
  }

  deleteSentMessage(message) {
    return this.http.delete<Message>(this.url + message.id + '/delete-user-sent', { headers: this.headers });
  }

  deleteReceivedMessage(message) {
    return this.http.delete<Message>(this.url + message.id + '/delete-user-received', { headers: this.headers });
  }

  markAsRead(userId, messageId) {
    return this.http.put<Message>(this.url + 'user/' + userId + '/received/' + messageId + '/mark-as-read', null, { headers: this.headers });
  }
}
