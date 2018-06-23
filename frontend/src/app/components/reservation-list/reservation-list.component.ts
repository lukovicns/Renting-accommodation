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

  constructor(private reservationService: ReservationService) { }

  ngOnInit() {
    this.reservationService.getUserReservations()
    .subscribe(res => {
      this.reservations = res;
    }, err => {
      console.log(err);
    });
  }
  
  cancelReservation(reservationId) {
    this.reservationService.cancelReservation(reservationId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      console.log(err);
    })
  }
}
