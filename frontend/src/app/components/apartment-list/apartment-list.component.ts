import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';
import { ActivatedRoute } from '@angular/router';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-apartment-list',
  templateUrl: './apartment-list.component.html',
  styleUrls: ['./apartment-list.component.css'],
  animations: [fadeIn()]
})
export class ApartmentListComponent implements OnInit {

  private image: String = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';
  private accommodationId: Number;
  private advancedOptions = false;
  private apartments = [];
  private reservations = [];
  private apartment = {};
  
  constructor(
    private apartmentService: ApartmentService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentService.getApartments()
    .subscribe(res => {
      for (let i = 0; i < res.length; i++) {
        if (res[i].accommodation['id'] == this.accommodationId) {
          this.apartments.push(res[i]);
        }
      }
    });
  }
}
