import { Component, OnInit } from '@angular/core';
import { AccommodationService } from '../../services/accommodation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Accommodation } from '../../models/Accommodation';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
// import { datepicker } from '../../../assets/js/script.js';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  animations: [fadeIn()]
})
export class AccommodationDetailComponent implements OnInit {

  private accommodationId: Number;
  private showOwnerInfo: boolean = false;
  private advancedOptions: boolean = false;
  private accommodation = {};
  private agent = {};

  constructor(
    private accommodationService: AccommodationService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) { }

  searchForm = this.formBuilder.group({
    city: ['', Validators.required],
    persons: [0, Validators.compose([
      Validators.required,
      Validators.min(1)
    ])]
  });

  messageForm = this.formBuilder.group({
    content: ['', Validators.required]
  });

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

  searchApartments() {
    this.router.navigate(['/accommodations/'+ this.accommodationId + '/search'], {
      queryParams: {
        'city': this.searchForm.value['city'],
        'persons': this.searchForm.value['persons']
      }
    })
  }

  sendMessage() {

  }

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    document.querySelector('#toggleOption').textContent = this.advancedOptions ? 'Hide' : 'Show';
  }
}
