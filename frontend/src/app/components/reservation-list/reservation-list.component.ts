import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
  animations: [fadeIn()]
})
export class ReservationListComponent implements OnInit {

  private reservations = [];
  private apartment = {};
  private exists = true;

  constructor(private reservationService: ReservationService) { }

  ngOnInit() {
    this.getReservations();
  }

  getReservations() {
    this.reservationService.getUserReservations()
    .subscribe(res => {
      this.reservations = res;
      if (this.reservations.length > 0) {
        this.apartment = this.reservations[0]['apartment'];
      }
    }, err => {
      console.log(err);
    });
  }

  removeReservation(reservationId) {
    this.reservationService.removeReservation(reservationId)
    .subscribe(res => {
      this.getReservations();
    }, err => {
      console.log(err);
    })
  }
}
