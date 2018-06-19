import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-approve-agents',
  templateUrl: './approve-agents.component.html',
  styleUrls: ['./approve-agents.component.css'],
  animations: [fadeIn()]
})
export class ApproveAgentsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
}
