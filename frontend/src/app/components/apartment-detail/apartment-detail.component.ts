import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccommodationService } from '../../services/accommodation.service';
import { ApartmentService } from '../../services/apartment.service';
import { fadeIn } from '../../animations';
import { ApartmentAdditionalServiceService } from '../../services/apartment-additional-service.service';
import { ReservationService } from '../../services/reservation.service';
import { UserService } from '../../services/user.service';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css'],
  animations: [fadeIn()]
})
export class ApartmentDetailComponent implements OnInit {

  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private hasReservation: boolean;
  private apartmentAdditionalServices = [];
  private accommodationId: Number;
  private apartmentId: Number;
  private apartment = {};
  private comments = [];

  constructor(
    private apartmentAdditionalServiceService: ApartmentAdditionalServiceService,
    private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private apartmentService: ApartmentService,
    private commentService: CommentService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.hasReservation = false;
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentId = parseInt(this.route.snapshot.params['apartmentId']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.apartmentService.getApartmentByAccommodationId(this.accommodationId, this.apartmentId)
      .subscribe(resp => {
        this.apartment = resp;
        this.apartmentAdditionalServiceService.getApartmentAdditionalServices(this.apartmentId)
        .subscribe(response => {
          this.apartmentAdditionalServices = response;
        }, err => {
          console.log(err);
        })
      }, err => {
        this.router.navigate(['accommodations/' + this.accommodationId]);
      })
      if (this.userService.getCurrentUser() != null) {
        this.reservationService.getUserReservationByApartmentId(this.apartmentId)
        .subscribe(reservation => {
          if (reservation != null) {
            this.hasReservation = true;
          }
        })
        this.commentService.getApartmentComments(this.apartmentId)
        .subscribe(res => {
          this.comments = res;
          console.log(res);
        });
      }
    }, err => {
      this.router.navigate(['accommodations']);
    })
  }
}
