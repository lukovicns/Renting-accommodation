import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Message } from '../models/Message';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
    
    private url: string = 'http://localhost:9000/restIntercepter/';

  constructor(private http: HttpClient) { }
  
    sendMessage(data) {
      return this.http.post(this.url + 'sendMessageToUser', data);
    }

    getSentMessages() {
      return this.http.get<Message[]>(this.url + 'getAgentSentMessages');
    }

    getReceivedMessages() {
      return this.http.get<Message[]>(this.url + 'getAgentReceivedMessages');
    }

    getSentMessage(messageId) {
      return this.http.get<Message>(this.url + 'getAgentSentMessage/'+ messageId);
    }

    getReceivedMessage(messageId) {
      return this.http.get<Message>(this.url + 'getAgentReceivedMessage/' + messageId);
    }

    deleteSentMessage(message) {
      return this.http.delete<Message>(this.url + 'deleteAgentSentMessage/'+ message.id);
    }

    deleteReceivedMessage(message) {
      return this.http.delete<Message>(this.url + 'deleteAgentSentMessage/'+ message.id);
    }

    markAsRead(messageId) {
      return this.http.put<Message>(this.url + 'markAsReadAgentMessage/' + messageId, null);
    }
}
