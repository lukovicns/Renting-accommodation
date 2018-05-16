import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../animations';

@Component({
  selector: 'app-accommodation-list',
  templateUrl: './accommodation-list.component.html',
  styleUrls: ['./accommodation-list.component.css'],
  animations: [fadeIn()]
})
export class AccommodationListComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  accommodations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
}
