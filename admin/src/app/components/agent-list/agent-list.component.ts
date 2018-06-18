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

  constructor(private agentService: AgentService) { }

  ngOnInit() {
    this.agentService.getAgents()
    .subscribe(res => {
      this.agents = res;
    }, err => {
      console.log(err);
    })
  }
}
