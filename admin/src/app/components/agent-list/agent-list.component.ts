import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AgentService } from '../../services/agent.service';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.css'],
  animations: [fadeIn()]
})
export class AgentListComponent implements OnInit {

  private agents = [];
  private errorMessage: boolean;

  constructor(private agentService: AgentService) { }

  ngOnInit() {
    this.agentService.getAgents()
    .subscribe(res => {
      this.agents = res;
    }, err => {
      console.log(err);
    })
  }

  approveAgent(agentId) {
    this.agentService.approveAgent(agentId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }

  declineAgent(agentId) {
    this.agentService.declineAgent(agentId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }

  removeAgentApproval(agentId) {
    this.agentService.removeAgentApproval(agentId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
