import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  animations: [fadeIn()]
})
export class AccommodationDetailComponent implements OnInit {

  constructor(private accommodationService : AccommodationService, private apartmentService : ApartmentService,
              private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router) { }
  
  title = 'The Booking App';
  accommodation:any;
  acc: any;
  apartments: any;
  message: string;
  aa: any;
  msg: string;
  show : boolean;
  accommodationId: any;
  temp: any;
  
  ngOnInit() {
      
      this.activatedRoute.params.subscribe((params: Params) => {
          this.accommodationId = params['id'];
        });
      
      let id: any;
      this.acc = this.accommodationService.getSelected();

      if(this.acc != undefined)
      {
          id = this.acc.id;
          this.temp = this.acc;
          this.accommodation = {
                  'id': this.temp.id,
                  'name': this.temp.name,
                  'type': this.temp.type,
                  'city': this.temp.city,
                  'street': this.temp.street,
                  'country': this.temp.country,
                  'description': this.temp.description,
                  'category': this.temp.category,
                  'image': this.temp.image 
           }
          
      }else if(this.acc == undefined)
      { 
          id = this.accommodationId;
          this.accommodationService.getAccommodation(id).subscribe(res => 
          {
              this.temp = res['return'];
              this.accommodation = {
                  'id': this.temp.id,
                  'name': this.temp.name,
                  'type': this.temp.type,
                  'city': this.temp.city,
                  'street': this.temp.street,
                  'country': this.temp.country,
                  'description': this.temp.description,
                  'category': this.temp.category,
                  'image': this.temp.image 
              }
          },
          err => {console.error(err); this.router.navigate(['/notFound']);
          });
      }
      
      this.accommodationService.getApartments(id)
      .subscribe(res => {
          console.log(typeof(res['return'])); 
//      let addService = res['return'].additionalService.split(";");
//      addService = Array.of(addService);
//      console.log(addService[0][0]);
      
      if(res['return'] == 'This accommodation has no apartments.')
      { 
          this.show = false;
          this.msg = res['return'];
          
      }else if(typeof(res).valueOf() == 'object' && res['return'].length == undefined)
      {
          this.show = true;
          this.apartments = res['return'];
          this.apartments = Array.of(this.apartments);
          
      }else
      {
          this.show = true;
          this.apartments = res['return'];
      }
    }, err => {console.log(err); 
        this.router.navigate(['/notFound'])
    });
  }
  
  deleteApartment(apartmentId){
      swal({
          text: 'Are you sure?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!',
          cancelButtonText: 'No, keep it'
              
      }).then((result) => {
          console.log(result.value);
          if (result.value) {
              this.accommodationService.deleteApartment(apartmentId).subscribe(res => {console.log('obrisano')})
              swal(
                'Deleted!',
                'success'
              ).then((result) => {
                  if (result.value) {
                      this.ngOnInit();
                  }
              });
          }
      });
  }
  
  getApartment(id){
//      this.apartmentService.getApartment(id);
      this.router.navigate(['/apartmentDetail/'+ id]);
  }

}
