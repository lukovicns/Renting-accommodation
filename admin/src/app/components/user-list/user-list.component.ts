import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  animations: [fadeIn()]
})
export class UserListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
