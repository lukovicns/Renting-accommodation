import { AccommodationService } from '../../services/accommodation.service';
import { datepicker, getFormattedDate } from '../../../assets/js/script.js';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';
import { CityService } from '../../services/city.service';

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
    private cityService: CityService,
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
    this.cityService.getCityByName(this.searchForm.value['city'])
    .subscribe(res => {
      this.router.navigate(['/accommodations/search'], {
        queryParams: {
          'city': res['id'],
          'persons': this.searchForm.value['persons'],
          'startDate': this.searchForm.value['startDate'],
          'endDate': this.searchForm.value['endDate']
        }
      })
    });
  }

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    document.querySelector('#toggleOption').textContent = this.advancedOptions ? 'Hide' : 'Show';
  }
}
