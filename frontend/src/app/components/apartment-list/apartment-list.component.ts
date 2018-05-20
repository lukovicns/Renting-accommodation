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

  constructor(private apartmentService: ApartmentService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.accommodationId = parseInt(this.route.snapshot.params['id']);
    this.apartmentService.getApartments()
    .subscribe(res => {
      console.log(res);
      for (let i = 0; i < res.length; i++) {
        if (res[i].accommodation['id'] == this.accommodationId) {
          this.apartments.push(res[i]);
        }
      }
    });
  }
}
