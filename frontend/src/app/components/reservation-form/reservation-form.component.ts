import { AccommodationService } from '../../services/accommodation.service';
import { ReservationService } from '../../services/reservation.service';
import { ApartmentService } from '../../services/apartment.service';
import { datepicker } from '../../../assets/js/script.js';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css'],
  animations: [fadeIn()]
})
export class ReservationFormComponent implements OnInit {

  private errorMessage: String;
  private accommodationId: Number;
  private apartmentId: Number;
  private accommodation = {};
  private apartment = {};

  constructor(
    private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private apartmentService: ApartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    datepicker();

    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentId = parseInt(this.route.snapshot.params['apartmentId']);

    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.accommodation = res;
      this.apartmentService.getApartmentByAccommodationId(this.accommodationId, this.apartmentId)
      .subscribe(res => this.apartment = res,
      err => {
        this.router.navigate(['accommodations/' + this.accommodationId]);
      });
    }, err => {
      this.router.navigate(['accommodations']);
    });
  }

  makeReservations() {
    const reservation = {
      'apartment': this.apartment,
      'startDate': document.querySelector('#startDate')['value'],
      'endDate': document.querySelector('#endDate')['value']
    }
    this.reservationService.makeReservations(reservation)
    .subscribe(res => {
      this.router.navigate(['reservations']);
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
