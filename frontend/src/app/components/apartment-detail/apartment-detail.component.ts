import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccommodationService } from '../../services/accommodation.service';
import { ApartmentService } from '../../services/apartment.service';
import { fadeIn } from '../../animations';
import { ApartmentAdditionalServiceService } from '../../services/apartment-additional-service.service';
import { ReservationService } from '../../services/reservation.service';
import { UserService } from '../../services/user.service';
import { CommentService } from '../../services/comment.service';
import { FormBuilder, Validators } from '@angular/forms';
import { RatingService } from '../../services/rating.service';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css'],
  animations: [fadeIn()]
})
export class ApartmentDetailComponent implements OnInit {

  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private hasReservation: boolean;
  private userRated: boolean;
  private errorMessage: string;
  private successMessage: string;
  private apartmentAdditionalServices = [];
  private accommodationId: Number;
  private apartmentId: Number;
  private rating: Number;
  private apartment = {};
  private comments = [];

  constructor(
    private apartmentAdditionalServiceService: ApartmentAdditionalServiceService,
    private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private apartmentService: ApartmentService,
    private commentService: CommentService,
    private ratingService: RatingService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  commentForm = this.formBuilder.group({
    comment: ['', Validators.required]
  });

  ngOnInit() {
    this.hasReservation = false;
    this.userRated = false;
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
      this.ratingService.getAverageRatingForApartment(this.apartmentId)
      .subscribe(res => {
        res == 0 ? this.rating = 0 : this.rating = parseFloat(Number(res).toFixed(2));
      }, err => {
        console.log(err);
      })
      if (this.userService.getCurrentUser() != null) {
        this.reservationService.getUserReservationByApartmentId(this.apartmentId)
        .subscribe(reservation => {
          if (reservation != null) {
            this.hasReservation = true;
          }
        })
        this.commentService.getApartmentApprovedComments(this.apartmentId)
        .subscribe(res => {
          this.comments = res;
        });
        this.ratingService.getUserRatingForApartment(this.apartmentId)
        .subscribe(res => {
          this.userRated = res != null && res['user'].id == this.userService.getCurrentUser()['id'] ? true: false;
        }, err => {
          this.errorMessage = err['error'];
        })
      }
    }, err => {
      this.router.navigate(['accommodations']);
    })
  }

  isAuthenticated() {
    return this.userService.getCurrentUser() != null;
  }

  addComment() {
    const data = {
      'apartment': this.apartment,
      'comment': this.commentForm.value['comment']
    }
    this.commentService.addComment(data)
    .subscribe(resp => {
      this.successMessage = 'Comment successfully submitted. Waiting for approval.';
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
    this.commentForm.reset();
  }

  rateApartment() {
    let rating = document.querySelector('input[name=rating]:checked')['value'];
    const data = {
      'user': this.userService.getCurrentUser(),
      'apartment': this.apartment,
      'rating': rating
    }
    this.ratingService.rateApartment(data)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      console.log(err);
    })
  }
}
