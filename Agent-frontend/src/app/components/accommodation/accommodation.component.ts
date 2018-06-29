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
    empty = true;
    
    title = 'The Booking App';
    
    model = new AccommodationType(1, this.accommodationTypes[0]);
    form: FormGroup;
  
  constructor(private accommodationService: AccommodationService, private formBuilder: FormBuilder, private router: Router) { 
  }
  
  fileName = '';
  imgName: string;
  accommodationForm = this.formBuilder.group({
     name: ['', Validators.required],
     accType: ['', Validators.required],
     city: ['', Validators.required],
     street: ['', Validators.required],
     description: [''],
     accCat: ['', Validators.required],
     image: ['', Validators.required]

  });
  
  url:string;
  urls:string;
  i = '';
  onFileChanged(event) {
      
      const file = event.target.files[0];
      console.log('file ' + file);
      
      if(this.imgName != undefined)
          this.imgName = this.imgName + '; ' + file.name;
      else 
          this.imgName = file.name;
      
      if (event.target.files && event.target.files[0]) {
          var reader = new FileReader();
          reader.readAsDataURL(event.target.files[0]); // read file as data url
          reader.onload = (event) => { // called once readAsDataURL is completed
          console.log(this.url);
          console.log(this.urls);
          this.url = reader.result;
          if(this.urls != undefined)
              this.urls = this.urls + 'ovo-je-separator' + this.url;
          else
              this.urls = this.url;
          }
          console.log(this.urls);
      }
//      this.accommodationForm.controls['image'].setValue(this.imgName ? this.imgName : '');
      console.log('imag ' + this.imgName);
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

  imgEmpty(){
      if(this.accommodationForm.controls['image'].value == '')
          this.empty = true;
      else
          this.empty = false;
  }
  
  addAccommodation() {
    this.accommodationService.addAccommodation(this.accommodationForm.value, this.urls)
    .subscribe(res => { 
        swal({
            text: res['return'],
            confirmButtonText: 'Ok',
        });
           this.router.navigate(['/accommodationsTable']);
      });
  }
}

