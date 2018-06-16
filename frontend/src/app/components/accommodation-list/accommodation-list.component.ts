import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accommodation-list',
  templateUrl: './accommodation-list.component.html',
  styleUrls: ['./accommodation-list.component.css'],
  animations: [fadeIn()]
})
export class AccommodationListComponent implements OnInit {

  private accommodations = [];

  constructor(
    private accommodationService: AccommodationService,
    private router: Router
  ) { }

  ngOnInit() {
    this.accommodationService.getAccommodations()
    .subscribe(res => this.accommodations = res);
  }
}
