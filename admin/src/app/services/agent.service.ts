import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Agent } from '../models/Agent';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private url: string = 'http://localhost:8081/api/agents/';

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
}
