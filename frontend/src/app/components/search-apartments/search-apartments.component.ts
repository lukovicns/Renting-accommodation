import { ApartmentService } from '../../services/apartment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { CityService } from '../../services/city.service';

@Component({
  selector: 'app-search-apartments',
  templateUrl: './search-apartments.component.html',
  styleUrls: ['./search-apartments.component.css'],
  animations: [fadeIn()]
})
export class SearchApartmentsComponent implements OnInit {

  private apartments: any = [];
  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private city = {};

  constructor(
    private apartmentService: ApartmentService,
    private cityService: CityService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    const city = this.route.snapshot.queryParams['city'];
    const persons = this.route.snapshot.queryParams['persons'];
    const startDate = this.route.snapshot.queryParams['startDate'];
    const endDate = this.route.snapshot.queryParams['endDate'];
    if (city == null || city == "" || persons == null || persons == "" ||
        startDate == null || startDate == "" || endDate == null || endDate == "") {
        this.router.navigate(['accommodations']);
      } else {
        this.cityService.getCity(city)
        .subscribe(res => {
          this.apartmentService.getApartmentsByQueryParams(res['id'], persons, startDate, endDate)
          .subscribe(resp => {
            this.apartments = resp;
          })
        }, err => {
          this.router.navigate(['accommodations']);
        })
      }
  }
}
