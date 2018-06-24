import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {

  private url = 'http://localhost:9000/restIntercepter';
  data = {};
  newPricePlan:any;
  allPricePlans: any;
  
  ngOnInit() {
  }
  //private url = 'http://localhost:8080/greeting';
  
  constructor(private http: HttpClient) { }

  getBedTypes() {
    return this.http.get(this.url + '/getBedTypes');

  }
  
  getAccommodationCategories() {
    return this.http.get(this.url + '/getAccommodationCategories');
  }
  
  getCities() {
    return this.http.get(this.url + '/getCities');
  }
  
  getAdditionalServices() {
      return this.http.get(this.url + '/getAdditionalServices');
  }
 
  getPricePlans() {
      return this.http.get(this.url + '/getPricePlans');
  }
 
  setNewPricePlan(newPricePlan){
      this.newPricePlan = newPricePlan;
      console.log(this.newPricePlan);
  }
  
  getNewPricePlans(){
      return this.newPricePlan;
  }
  
  addApartment(apartment, checkedItems, pricePlans, accommodationId, imgUrl) {
      
    this.data = {
      'name': apartment.name,
      'bedType': apartment.bedType,
      'size': apartment.size,
      'numOfRooms': apartment.numOfRooms,
      'numOfGuests': apartment.numOfGuests,
      'description': apartment.description,
      'image': imgUrl,
      'additionalService': checkedItems,
      'pricePlans': pricePlans
    };
    
    return this.http.post(this.url + '/addApartment/' + accommodationId, this.data);
  }
  
  addNewPricePlan(pricePlan, apartmentId){
      var data = {
          'startDate': pricePlan.startDate,
          'endDate': pricePlan.endDate,
          'price': pricePlan.price
      };
      console.log(data);
      console.log(apartmentId);
      return this.http.post(this.url + '/addPricePlan/' + apartmentId, data);
  }
  
  getApartment(id){
      return this.http.get(this.url + '/getApartment/' + id);
  }
  
  addReservation(data, apartmentId){
      let  newdata = {
              'startDate': data.startDateR,
              'endDate': data.endDateR
            };
      return this.http.post(this.url + '/addReservation/' + apartmentId, newdata);
  }
}
