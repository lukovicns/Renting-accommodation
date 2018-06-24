import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import swal from 'sweetalert2';
//import { datepicker, getFormattedDate } from '../../../assets/js/script.js';

@Component({
  selector: 'app-apartment-detail',
  templateUrl: './apartment-detail.component.html',
  styleUrls: ['./apartment-detail.component.css'],
  animations: [fadeIn()]
})
export class ApartmentDetailComponent implements OnInit {

    constructor(private apartmentService : ApartmentService,
                private activatedRoute: ActivatedRoute, 
                private formBuilder: FormBuilder,
                private router: Router) { }
    
    apartmentId: any;
    title = 'The Booking App';
    apartment = {};
    additionalServices = [];
    pricePlans = [];
    jsonPP: any;
    
//    pomocne varijable za prikaz odredjenih formi 
    showAS = false;
    showPP = false;
    addNewPP = false;
    addNewR = false;
    
    pricePlanForm= this.formBuilder.group({
        startDatePP: ['', Validators.required],
        endDatePP: ['', Validators.required],
        pricePP: ['', Validators.required]
     });
    
    reservationForm = this.formBuilder.group({
        startDateR: ['', Validators.required],
        endDateR: ['', Validators.required]
     });
    
  ngOnInit() {
      this.activatedRoute.params.subscribe((params: Params) => {
          this.apartmentId = params['id'];
        });
      
      this.getData();
//      datepicker();
  }
  
  getData(){
      this.apartmentService.getApartment(this.apartmentId).subscribe(res => {
          console.log(res['return']);
          let temp = res['return'];
          
          this.apartment = {
              'name': temp.name,
              'bedType': temp.bedType,
              'size': temp.size,
              'numOfRooms': temp.numOfRooms,
              'numOfGuests': temp.numOfGuests,
              'description': temp.description,
              'image': temp.image,
              'additionalService': temp.additionalService,
              'pricePlans': temp.pricePlans
          };
          
          for(let i = 0; i < this.apartment['additionalService'].split(";").length - 1; i++)
          {
              if(this.additionalServices.indexOf(this.apartment['additionalService'].split(";")[i]) == -1)
              this.additionalServices.push(this.apartment['additionalService'].split(";")[i]);
          }
          
          this.jsonPP = JSON.parse(this.apartment['pricePlans']);
//          console.log(this.jsonPP);
          
//          this.additionalServices = 
          
      }, err => {console.log(err); 
          this.router.navigate(['/notFound'])
      });
      
  }
  
  onCheckboxChangeAS(event){
      this.showAS = !this.showAS;
  }
  
  onCheckboxChangePP(event){
      this.showPP = !this.showPP;
  }

  showP(){
      this.addNewR = false;
      this.addNewPP = !this.addNewPP;
  }
  
  showR(){
      this.addNewR = !this.addNewR;
      this.addNewPP = false;
  }
  
  addPricePlan(){
      console.log(this.pricePlanForm.value);
      this.apartmentService.addNewPricePlan(this.pricePlanForm.value, this.apartmentId).subscribe(res => {console.log('jfkajfkla');
              
              this.addNewPP = false;
      this.getData()});
      
      console.log("tttt");
  }
  
  addReservation(){
      console.log(this.reservationForm.value);
      this.apartmentService.addReservation(this.reservationForm.value, this.apartmentId).subscribe(res =>
      console.log(res);
      );
  }
}
