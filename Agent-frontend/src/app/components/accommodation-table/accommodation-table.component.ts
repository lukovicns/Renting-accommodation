import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-accommodation-table',
  templateUrl: './accommodation-table.component.html',
  styleUrls: ['./accommodation-table.component.css'],
  animations: [fadeIn()]
})
export class AccommodationTableComponent implements OnInit {

    title = 'The Booking App';
    accommodations: any;
    accommodation = [];
    data = {};
    constructor(private accommodationService : AccommodationService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
      this.getData();
  }

  onSelect(selectedItem: any) {
//      this.accommodationService.currentMessage.subscribe(message => this.accommodation = message)
      this.accommodation = selectedItem.name;
      this.data = {
          'id': selectedItem.id,
          'name': selectedItem.name,
          'type': selectedItem.type,
          'city': selectedItem.city,
          'street': selectedItem.street,
          'description': selectedItem.description,
          'category': selectedItem.category,
          'country': selectedItem.country,
          'image': selectedItem.image
    };
      console.log(this.data['id']);
      this.accommodationService.selectedItem(this.data);
  }
  msg: string;
  show : boolean;
  getData(){
      this.accommodationService.getAccommodations()
      .subscribe(
              res => {
                  
                  if(typeof(res).valueOf() == 'object' && res['return'].length == undefined)
                  {
                      this.show = true;
                      this.accommodations = res['return'];
                      this.accommodations = Array.of(this.accommodations);
                      
                  }else if(res['return'] == "No accommodations available.")
                  {
                      this.msg = res['return'];
                      this.show = false;
                      
                  }else
                  {
                      this.show = true;
                      this.accommodations = res['return'];
                  }
              },
              err => console.error(err), 
    );
  }
  
  deleteAccommodation(id){
      swal({
          text: 'Are you sure?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!',
          cancelButtonText: 'No, keep it'
              
      }).then((result) => {
          console.log(result.value);
          if (result.value) {
              this.accommodationService.deleteAccommodation(id).subscribe(res => {console.log('obrisano')})
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
}
