import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

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

  accommodationForm = this.formBuilder.group({
    city: ['', Validators.required],
    persons: [0, Validators.compose([
      Validators.required,
      Validators.min(1)
    ])]
  });

  ngOnInit() {
    document.querySelector('#toggleOption').textContent = 'Show';
    this.accommodationService.getAccommodations()
    .subscribe(res => this.accommodations = res);
  }

  searchAccommodations() {
    this.router.navigate(['/accommodations/search'], {
      queryParams: {
        'city': this.accommodationForm.value['city'],
        'persons': this.accommodationForm.value['persons']
      }
    })
  }

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    if (this.advancedOptions) {
      document.querySelector('#toggleOption').textContent = 'Hide';
    } else {
      document.querySelector('#toggleOption').textContent = 'Show';
    }
  }
}
