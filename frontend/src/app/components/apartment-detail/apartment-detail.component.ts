import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccommodationService } from '../../services/accommodation.service';
import { ApartmentService } from '../../services/apartment.service';
import { fadeIn } from '../../animations';
import { ApartmentAdditionalServiceService } from '../../services/apartment-additional-service.service';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css'],
  animations: [fadeIn()]
})
export class ApartmentDetailComponent implements OnInit {

  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private apartmentAdditionalServices = [];
  private accommodationId: Number;
  private apartmentId: Number;
  private apartment = {};

  constructor(
    private apartmentAdditionalServiceService: ApartmentAdditionalServiceService,
    private accommodationService: AccommodationService,
    private apartmentService: ApartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentId = parseInt(this.route.snapshot.params['apartmentId']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.apartmentService.getApartment(this.apartmentId)
      .subscribe(resp => {
        this.apartment = resp;
        this.apartmentAdditionalServiceService.getApartmentAdditionalServices(this.apartmentId)
        .subscribe(response => {
          this.apartmentAdditionalServices = response;
          console.log(response);
        }, err => {
          console.log(err);
        })
      }, err => {
        this.router.navigate(['accommodations/' + this.accommodationId]);
      })
    }, err => {
      this.router.navigate(['accommodations']);
    })
  }
}
