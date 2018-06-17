import { Component, OnInit } from '@angular/core';
import { AccommodationService } from '../../services/accommodation.service';
import { Router, ActivatedRoute } from '@angular/router';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';

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
    private apartmentService: ApartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => this.accommodation = res);
    const city = this.route.snapshot.params['city'];
    const persons = this.route.snapshot.queryParams['persons'];
    const startDate = this.route.snapshot.queryParams['startDate'];
    const endDate = this.route.snapshot.queryParams['endDate'];
    if (city === null || persons == null || startDate == null || endDate == null) {
        this.router.navigate(['accommodations/' + this.accommodationId]);
    } else {
      // this.apartmentService.getApartmentsByQueryParams(accommodationId, city, persons, startDate, endDate)
      // .subscribe(res => {
      //   console.log(res);
      // }, err => {
      //   console.log(err);
      // })
    }
  }
}
