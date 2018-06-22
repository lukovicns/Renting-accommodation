import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-price-plan',
  templateUrl: './price-plan.component.html',
  styleUrls: ['./price-plan.component.css'],
  animations: [fadeIn()]
})
export class PricePlanComponent implements OnInit {

  constructor(private apartmentService: ApartmentService, private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router) { }

  accommodationId: string;
  ngOnInit() {
  }
  
  pricePlanForm= this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      price: ['', Validators.required]
   });
  
  /*addPricePlan(){
      this.accommodationId = this.apartmentService.getAccommodationId();
      this.router.navigate(['/apartment', ]);
      this.apartmentService.setNewPricePlan(this.pricePlanForm.value);
      
  }*/
}
