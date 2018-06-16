import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-agent-list',
  templateUrl: './agent-list.component.html',
  styleUrls: ['./agent-list.component.css'],
  animations: [fadeIn()]
})
export class AgentListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
