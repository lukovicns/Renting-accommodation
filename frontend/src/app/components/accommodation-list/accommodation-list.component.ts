import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { datepicker, getFormattedDate } from '../../../assets/js/script.js';

@Component({
  selector: 'app-accommodation-list',
  templateUrl: './accommodation-list.component.html',
  styleUrls: ['./accommodation-list.component.css'],
  animations: [fadeIn()]
})
export class AccommodationListComponent implements OnInit {

  private accommodations = [];
  private advancedOptions: boolean = false;

  constructor(
    private accommodationService: AccommodationService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    datepicker();
    this.accommodationService.getAccommodations()
    .subscribe(res => this.accommodations = res);
  }

  searchForm = this.formBuilder.group({
    city: ['', Validators.required],
    persons: [0, Validators.compose([
      Validators.required,
      Validators.min(1)
    ])],
    startDate: [getFormattedDate(), Validators.required],
    endDate: [getFormattedDate(), Validators.required]
  });

  searchApartments() {
    // this.router.navigate(['/accommodations/'+ this.accommodationId + '/search'], {
    //   queryParams: {
    //     'city': this.searchForm.value['city'],
    //     'persons': this.searchForm.value['persons'],
    //     'startDate': this.searchForm.value['startDate'],
    //     'endDate': this.searchForm.value['endDate']
    //   }
    // })
  }

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    document.querySelector('#toggleOption').textContent = this.advancedOptions ? 'Hide' : 'Show';
  }
}
