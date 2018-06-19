import { ApartmentService } from '../../services/apartment.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { fadeIn } from '../../animations';
import { UserService } from '../../services/user.service';
import { MessageService } from '../../services/message.service';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-apartment-list',
  templateUrl: './apartment-list.component.html',
  styleUrls: ['./apartment-list.component.css'],
  animations: [fadeIn()]
})
export class ApartmentListComponent implements OnInit {

  private image: string = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private accommodationId: Number;
  private advancedOptions = false;
  private apartments = [];
  private reservations = [];
  private apartment = {};
  
  constructor(
    private apartmentService: ApartmentService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentService.getApartments(this.accommodationId)
    .subscribe(res => {
      this.apartments = res;
    });
  }
}
