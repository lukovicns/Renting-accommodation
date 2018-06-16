import { Component, OnInit } from '@angular/core';
import { AccommodationService } from '../../services/accommodation.service';
import { Router, ActivatedRoute } from '@angular/router';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-search-apartments',
  templateUrl: './search-apartments.component.html',
  styleUrls: ['./search-apartments.component.css'],
  animations: [fadeIn()]
})
export class SearchApartmentsComponent implements OnInit {

  private accommodationId: Number;
  private accommodation = {};

  constructor(
    private accommodationService: AccommodationService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => this.accommodation = res);
    if (this.route.snapshot.params['city'] === null ||
        this.route.snapshot.queryParams['persons'] == null) {
        this.router.navigate(['accommodations/' + this.accommodationId]);
    }
  }
}
