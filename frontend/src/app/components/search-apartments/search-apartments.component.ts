import { ApartmentService } from '../../services/apartment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { CityService } from '../../services/city.service';
import { sortTable } from '../../../assets/js/script';

@Component({
  selector: 'app-search-apartments',
  templateUrl: './search-apartments.component.html',
  styleUrls: ['./search-apartments.component.css'],
  animations: [fadeIn()]
})
export class SearchApartmentsComponent implements OnInit {

  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private apartments = [];

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
    const type = this.route.snapshot.queryParams['type'];
    const category = this.route.snapshot.queryParams['category'];
    if (city == null || city == "" || persons == null || isNaN(Number(persons)) ||
    startDate == null || startDate == "" || endDate == null || endDate == "") {
      this.router.navigate(['accommodations']);
    } else {
      this.initSearchedApartments(city, startDate, endDate, persons, type, category);
    }
  }

  initSearchedApartments(city, startDate, endDate, persons, type, category) {
    this.cityService.getCity(city)
    .subscribe(res => {
      if (type == null && category == null) {
        this.apartmentService.getApartmentsByBasicQueryParams(res['id'], persons, startDate, endDate)
        .subscribe(resp => {
          this.apartments = resp;
        })
      } else {
        this.apartmentService.getApartmentsByAdvancedQueryParams(res['id'], persons, startDate, endDate, type, category)
        .subscribe(resp => {
          this.apartments = resp;
        })
      }
    }, err => {
      this.router.navigate(['accommodations']);
    })
  }

  sortTable(n) {
    sortTable(n);
  }
}
