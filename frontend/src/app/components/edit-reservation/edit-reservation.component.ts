import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { ApartmentService } from '../../services/apartment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-edit-reservation',
  templateUrl: './edit-reservation.component.html',
  styleUrls: ['./edit-reservation.component.css'],
  animations: [fadeIn()]
})
export class EditReservationComponent implements OnInit {

  private accommodationId: Number;
  private apartmentId: Number;
  private reservationId: Number;
  private accommodation = {};
  private apartment = {};
  private reservation = {};

  constructor(
    private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private apartmentService: ApartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentId = parseInt(this.route.snapshot.params['apartmentId']);
    this.reservationId = parseInt(this.route.snapshot.params['reservationId']);

    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.accommodation = res;
      this.apartmentService.getApartmentByAccommodationId(this.accommodationId, this.apartmentId)
      .subscribe(resp => {
        this.apartment = resp;
        this.reservationService.getUserReservationByApartmentId(this.apartmentId)
        .subscribe(response => {
          if (response['id'] != this.reservationId) {
            this.router.navigate(['accommodations/' + this.accommodationId + '/apartments/' + this.apartmentId]);
          }
        }, err => {
          this.router.navigate(['accommodations/' + this.accommodationId + '/apartments/' + this.apartmentId]);
        })
      }, err => {
        this.router.navigate(['accommodations/' + this.accommodationId]);
      });
    }, err => {
      this.router.navigate(['accommodations']);
    });
  }
}
