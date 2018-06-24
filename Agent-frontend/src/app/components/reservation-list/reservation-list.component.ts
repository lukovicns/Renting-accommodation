import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ReservationService } from '../../services/reservation.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css'],
  animations: [fadeIn()]
})
export class ReservationListComponent implements OnInit {
    reservations: any;
    msg: string;
    show : boolean;
    confirmed = false;

  constructor(private reservationService: ReservationService, 
              private activatedRoute: ActivatedRoute, 
              private formBuilder: FormBuilder, 
              private router: Router) { }

  ngOnInit() {
      this.reservationService.getReservations().subscribe(
              res => {
                  console.log(res['return']);
                  if(res['return'] == 'There are no reservatoions.')
                  { 
                      this.show = false;
                      this.msg = res['return'];
                      
                  }else if(typeof(res).valueOf() == 'object' && res['return'].length == undefined)
                  {
                      this.show = true;
                      this.reservations = res['return'];
                      this.reservations = Array.of(this.reservations);
                  }else
                  {
                      console.log('tu');
                      this.show = true;
                      this.reservations = res['return'];
                  }
                }, err => {console.log(err); 
                    this.router.navigate(['/notFound'])
                });
      }

  
  confirmReservation(id)
  {
      this.confirmed = true;
      console.log(this.confirmed);
      this.reservationService.confirmReservation(id).subscribe(
              res => {
                  console.log(res['return']);
                  
                 });
                  
  }
}
