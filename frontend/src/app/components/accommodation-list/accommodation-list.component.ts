import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';

@Component({
  selector: 'app-accommodation-list',
  templateUrl: './accommodation-list.component.html',
  styleUrls: ['./accommodation-list.component.css'],
  animations: [fadeIn()]
})
export class AccommodationListComponent implements OnInit {

  private accommodations = [];

  constructor(private accommodationService: AccommodationService) { }

  ngOnInit() {
    this.accommodationService.getAccommodations()
    .subscribe(res => this.accommodations = res);
  }

  searchAccommodations() {
    
  }
}
