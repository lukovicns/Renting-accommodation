import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationType } from '../../models/AccommodationType';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-accommodation',
  templateUrl: './accommodation.component.html',
  styleUrls: ['./accommodation.component.css'],
  animations: [fadeIn()]
})
export class AccommodationComponent implements OnInit {
    accommodationTypes = [];
    accommodationCategory = [];
    cities = [];
    images = null;
  
    title = 'The Booking App';
    
    model = new AccommodationType(1, this.accommodationTypes[0]);
    form: FormGroup;
  
  constructor(private accommodationService: AccommodationService, private formBuilder: FormBuilder, private router: Router) { 
  }
  
  accommodationForm = this.formBuilder.group({
     name: ['', Validators.required],
     accType: ['', Validators.required],
     city: ['', Validators.required],
     street: ['', Validators.required],
     description: ['', Validators.required],
     accCat: ['', Validators.required],
     image: ['', Validators.required]

  });
  
  url:string;
  urls:string;
  imgName:string;
  i = '';
  onFileChanged(event) {
      
      const file = event.target.files[0];
      if(this.imgName != undefined)
          this.imgName = this.imgName + '; ' + file.name;
      else 
          this.imgName = file.name;

      if (event.target.files && event.target.files[0]) {
          var reader = new FileReader();
          reader.readAsDataURL(event.target.files[0]); // read file as data url
          reader.onload = (event) => { // called once readAsDataURL is completed
          
          this.url = reader.result;
          if(this.urls != undefined)
              this.urls = this.urls + 'ovo-je-separator' + this.url;
          else
              this.urls = this.url;
          }
      }
  }
  
  getData(): void {
         this.accommodationService.getAccommodationTypes().subscribe(
                 resultArray => { this.accommodationTypes = resultArray['return']; }
             );
          this.accommodationService.getAccommodationCategories().subscribe(
                  resultArray => { this.accommodationCategory = resultArray['return']; }
          );
    
          this.accommodationService.getCities().subscribe(
                  resultArray => { this.cities = resultArray['return']; }  
          );
     }

  onChange(animal) {
   alert(animal);
  }
  
  ngOnInit(): void {
         this.getData();
  }

  
  addAccommodation() {
    this.accommodationService.addAccommodation(this.accommodationForm.value, this.urls)
      .subscribe(res => {
          swal({
              text: res['return'],
              confirmButtonText: 'Ok',
          }).then((result) => {
              if (result.value) {
                  this.router.navigate(['/accommodationsTable']);
              }
          });
      });
  }
}

