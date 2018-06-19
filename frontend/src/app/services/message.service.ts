import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Message } from '../models/Message';

@Injectable()
export class MessageService {

  private url: string = 'http://localhost:8081/api/messages/';

  constructor(private http: HttpClient) { }

  sendMessage(data) {
    return this.http.post(this.url + 'user-to-agent', data);
  }

  getSentMessages(userId) {
    return this.http.get<Message[]>(this.url + 'user/' + userId + '/sent');
  }

  getReceivedMessages(userId) {
    return this.http.get<Message[]>(this.url + 'user/' + userId + '/received');
  }

  getSentMessage(userId, messageId) {
    return this.http.get<Message>(this.url + 'user/' + userId + '/sent/' + messageId);
  }

  getReceivedMessage(userId, messageId) {
    return this.http.get<Message>(this.url + 'user/' + userId + '/received/' + messageId);
  }

  deleteSentMessage(message) {
    return this.http.delete<Message>(this.url + message.id + '/delete-user-sent');
  }

  deleteReceivedMessage(message) {
    return this.http.delete<Message>(this.url + message.id + '/delete-user-received');
  }

  markAsRead(userId, messageId) {
    return this.http.put<Message>(this.url + 'user/' + userId + '/received/' + messageId + '/mark-as-read', null);
  }
}
