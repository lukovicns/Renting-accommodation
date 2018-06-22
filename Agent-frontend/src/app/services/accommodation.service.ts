
import {throwError as observableThrowError, Observable} from 'rxjs';
import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Accommodation } from '../models/Accommodation';
import { AccommodationType } from '../models/AccommodationType';
import { HttpHeaders } from '@angular/common/http';
import { ResponseType } from '@angular/http';
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class AccommodationService implements OnInit {
  

  private url = 'http://localhost:9000/restIntercepter';
  data = {};
  title = "tttt";
  
  ngOnInit() {
      
  }
  
  messageSource: any;
  currentMessage: any;
  accommodation: any;
  //private url = 'http://localhost:8080/greeting';
  
  constructor(private http: HttpClient) { }

  getAccommodationTypes() {
    return this.http.get(this.url + '/getAccommodationTypes');

  }
  
  getAccommodationCategories() {
    return this.http.get(this.url + '/getAccommodationCategories');
  }
  
  getCities() {
    return this.http.get(this.url + '/getCities');
  }

   private handleError(error: Response) {
       return observableThrowError(error.statusText);
   }

   getAccommodations(){
       return this.http.get(this.url + '/getAllAccommodations');
   }
   selectedItem(accommodation) {
       const ac = accommodation;
       
       this.messageSource = new BehaviorSubject(accommodation);
       this.currentMessage = this.messageSource.asObservable();
       this.accommodation = {
           'id': accommodation.id,
           'name': accommodation.name,
           'type': accommodation.type,
           'city': accommodation.city,
           'street': accommodation.street,
           'description': accommodation.description,
           'category': accommodation.category,
           'country': accommodation.country,
           'image': accommodation.image 
       }
   }
   
   getSelected() {
       return this.accommodation;
   }
   
  addAccommodation(accommodation, imgUrls) {
    this.data = {
      'name': accommodation.name,
      'type': accommodation.accType,
      'city': accommodation.city,
      'street': accommodation.street,
      'description': accommodation.description,
      'category': accommodation.accCat,
      'image': imgUrls
    };

    return this.http.post(this.url + '/addAccommodation', this.data);
  }

  getApartments(id){
      return this.http.get(this.url + '/getApartments/' + id);
  }
  
  deleteAccommodation(id){
      return this.http.delete(this.url + '/deleteAccommodation/' + id);
  }
  
  deleteApartment(id){
      return this.http.delete(this.url + '/deleteApartment/' + id);
  }
  
  getAccommodation(id){
      return this.http.get(this.url + '/getAccommodation/' + id);
  }
}
