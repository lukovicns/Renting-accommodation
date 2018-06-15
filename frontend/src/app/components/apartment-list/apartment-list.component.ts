import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-apartment-list',
  templateUrl: './apartment-list.component.html',
  styleUrls: ['./apartment-list.component.css'],
  animations: [fadeIn()]
})
export class ApartmentListComponent implements OnInit {

  private apartments = [];
  private accommodationId: Number;
  private advancedOptions = false;
  private image: String = 'https://t-ec.bstatic.com/images/hotel/max1280x900/120/120747263.jpg';

  constructor(private apartmentService: ApartmentService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    // document.querySelector('#showOption').textContent = 'Show';
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

  toggleOptions() {
    this.advancedOptions = !this.advancedOptions;
    // let showOption = document.querySelector('#showOption');
    // this.advancedOptions == true ? showOption.textContent = 'Hide' : showOption.textContent = 'Show';
  }
}
