import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Agent } from '../models/Agent';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private url: string = 'https://localhost:8081/api/agents/';
  private headers = new HttpHeaders()
  .append('Content-Type', 'application/json')
  .append('Authorization', 'Bearer ' + localStorage.getItem('token'));

  constructor(private http: HttpClient) { }

  getAgents() {
    return this.http.get<Agent[]>(this.url);
  }

  getAgent(agentId) {
    return this.http.get<Agent>(this.url + agentId);
  }

  getAgentByBusinessId(businessId) {
    return this.http.get<Agent>(this.url + '/business-id/' + businessId);
  }

  approveAgent(agentId) {
    return this.http.put<Agent>(this.url + agentId + '/approve', null, { headers: this.headers });
  }

  declineAgent(agentId) {
    return this.http.put<Agent>(this.url + agentId + '/decline', null, { headers: this.headers });
  }

  removeAgentApproval(agentId) {
    return this.http.put<Agent>(this.url + agentId + '/remove-approval', null, { headers: this.headers });
  }
}
