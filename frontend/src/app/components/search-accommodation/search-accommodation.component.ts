import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-search-accommodation',
  templateUrl: './search-accommodation.component.html',
  styleUrls: ['./search-accommodation.component.css'],
  animations: [fadeIn()]
})
export class SearchAccommodationComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    console.log(this.route.snapshot.queryParams);
    if (this.route.snapshot.params['city'] === null ||
        this.route.snapshot.queryParams['persons'] == null) {
        this.router.navigate(['accommodations']);
    }
  }
}
