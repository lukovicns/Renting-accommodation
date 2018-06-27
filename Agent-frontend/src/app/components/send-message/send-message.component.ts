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
  private message = {}
  private messageId : any;
  
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
      this.messageId = parseInt(this.route.snapshot.params['id']);
      this.messageService.getReceivedMessage(this.messageId).subscribe(res => {
          this.message = res['return'];
          console.log(res);
      }, err => {
        console.log(err);
      })
  }

  sendMessage() {
    const data = {
      'id' : this.messageId,
      'text': this.messageForm.value['content']
    }
    this.messageService.sendMessage(data)
    .subscribe(res => {
      this.successMessage = 'Message succesfully sent.';
      this.messageForm.reset();
    }, err => {
      console.log(err);
    })
  }
}
