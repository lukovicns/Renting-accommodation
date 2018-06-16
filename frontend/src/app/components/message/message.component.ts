import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css'],
  animations: [fadeIn()]
})
export class MessageComponent implements OnInit {

  private accommodationId: Number;
  private accommodation = {};
  private agent = {};

  constructor(
    private accommodationService: AccommodationService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.accommodation = res;
      this.agent = this.accommodation['agent'];
    }, err => {
      console.log(err);
    })
  }
}
