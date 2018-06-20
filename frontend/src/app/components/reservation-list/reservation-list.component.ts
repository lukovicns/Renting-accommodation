import { ReservationService } from '../../services/reservation.service';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
  animations: [fadeIn()]
})
export class ReservationListComponent implements OnInit {

  private reservations = [];
  private exists = true;

  constructor(private reservationService: ReservationService) { }

  ngOnInit() {
    this.reservationService.getUserReservations()
    .subscribe(res => {
      this.reservations = res;
    }, err => {
      console.log(err);
    });
  }

  getReservations() {
    this.reservationService.getUserReservations()
    .subscribe(res => {
      this.reservations = res;
    }, err => {
      console.log(err);
    });
  }

  editReservation(reservationId) {
    
  }

  cancelReservation(reservationId) {
    this.reservationService.cancelReservation(reservationId)
    .subscribe(res => {
      this.getReservations();
    }, err => {
      console.log(err);
    })
  }
}
