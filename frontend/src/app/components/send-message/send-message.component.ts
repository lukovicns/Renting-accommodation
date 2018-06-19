import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ActivatedRoute, Router } from '@angular/router';
import { ApartmentService } from '../../services/apartment.service';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { MessageService } from '../../services/message.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-send-message',
  templateUrl: './send-message.component.html',
  styleUrls: ['./send-message.component.css'],
  animations: [fadeIn()]
})
export class SendMessageComponent implements OnInit {

  private successMessage: String;
  private accommodationId: Number;
  private apartmentId: Number;
  private accommodation = {};
  private apartment = {};

  constructor(
    private accommodationService: AccommodationService,
    private reservationService: ReservationService,
    private apartmentService: ApartmentService,
    private messageService: MessageService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  messageForm = this.formBuilder.group({
    content: ['', Validators.required]
  })

  ngOnInit() {
      this.accommodationId = parseInt(this.route.snapshot.params['id']);
      this.apartmentId = parseInt(this.route.snapshot.params['apartmentId']);
      this.reservationService.getUserReservationByApartmentId(this.apartmentId)
      .subscribe(res => {
        if (res != null) {
          this.accommodationService.getAccommodation(this.accommodationId)
          .subscribe(res => this.accommodation = res,
            err => {
            this.router.navigate(['/accommodations'])
          })
          this.apartmentService.getApartment(this.apartmentId)
          .subscribe(res => this.apartment = res,
            err => {
            this.router.navigate(['/accommodations/' + this.accommodationId]);
          });
        } else {
          this.router.navigate(['/accommodations/' + this.accommodationId + '/apartments/' + this.apartmentId])
        }
      }, err => {
        console.log(err);
      })
  }

  sendMessage() {
    const data = {
      'apartment': this.apartmentId,
      'user': this.userService.getCurrentUser()['id'],
      'agent': this.accommodation['agent'].id,
      'text': this.messageForm.value['content']
    }
    this.messageService.sendMessage(data)
    .subscribe(res => {
      this.successMessage = 'You have successfully sent message to ' + this.accommodation['agent'].email;
      this.messageForm.reset();
    }, err => {
      console.log(err);
    })
  }
}
