import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from './services/user.service';
import { ReservationService } from './services/reservation.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private userReservations = [];

  ngOnInit() {
    this.userIsLoggedIn();
  }

  constructor(
    private reservationService: ReservationService,
    private userService: UserService,
    private router: Router,
  ) { }

  userIsLoggedIn() {
    return this.userService.getCurrentUser() != null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }
}
