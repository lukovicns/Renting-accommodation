import { Component, OnInit } from '@angular/core';
import { AccommodationService } from '../../services/accommodation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Accommodation } from '../../models/Accommodation';
import { fadeIn } from '../../animations';
// import { datepicker } from '../../../assets/js/script.js';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  animations: [fadeIn()]
})
export class AccommodationDetailComponent implements OnInit {

  private accommodationId: Number;
  private accommodation = {};
  private agent = {};
  private showOwnerInfo: boolean = false;

  constructor(
    private accommodationService: AccommodationService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.accommodationService.getAccommodation(this.accommodationId)
    .subscribe(res => {
      this.accommodation = new Accommodation(
        res.id, res.name, res.type, res.city, res.street, res.description,
        res.category, res.agent, res.images, res.imageList
      );
      this.agent = res.agent;
    }, err => {
      this.router.navigate(['**']);
    });
  }
}
