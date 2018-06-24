import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import {ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import {DomSanitizer} from '@angular/platform-browser';
import { SecurityContext } from "@angular/core";
import {AccommodationService } from '../../services/accommodation.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: 'app-apartment-images',
    templateUrl: './apartment-images.component.html',
    styleUrls: ['./apartment-images.component.css']
  })
export class ApartmentImagesComponent implements OnInit {
    images = [];
    imag;
    apartmentId: any;
    apartment: any;
    temp: any;
    data:any;
    
  constructor(private apartmentService : ApartmentService, 
          private accommodationService : AccommodationService, 
          private formBuilder: FormBuilder, 
          private router: Router, 
          private dom: DomSanitizer,
          private activatedRoute: ActivatedRoute) {
//      <img [src]="_DomSanitizationService.bypassSecurityTrustUrl(imgSrcProperty)"/>
      
      this.activatedRoute.params.subscribe((params: Params) => {
          this.apartmentId = params['id'];
        });
      
      this.apartmentService.getApartment(this.apartmentId).subscribe(res => 
      {
          this.apartment = res['return'];
          console.log(this.apartment);
          this.data = {
                  'name': this.apartment.name,
                  'bedType': this.apartment.bedType,
                  'size': this.apartment.size,
                  'numOfRooms': this.apartment.numOfRooms,
                  'numOfGuests': this.apartment.numOfGuests,
                  'description': this.apartment.description
//                  'image': this.imgUrl,
//                  'additionalService': this.checkedItems,
//                  'pricePlans': this.pricePlans
                };
          let imgs = this.data.image.split(';');
          
          for(let i = 0; i < imgs.length - 1; i++)
          {
              console.log(imgs[i]);
              let pic = imgs[i].split("imgs")[1];
              console.log(pic);
//              this.imag = dom.bypassSecurityTrustUrl("../assets/imgs/" + pic);
             this.images.push(dom.bypassSecurityTrustUrl("../assets/imgs/" + pic)); 
             
          }
//          console.log(this.accommodation.image);
      },
      err => {this.router.navigate(['/notFound']);
      });
      
//      this.images = dom.bypassSecurityTrustUrl("../assets/out29.png");
      /*    [
                     {"url":"G://tanja//slike//out-1.png"},
                     {"url":"G://tanja//slike//out9.png"}
                    
                       ];*/
//      var imageFilePath = $"{ImageFolder}{imgId}.jpg";
//      var imageFileStream = System.IO.File.OpenRead(imageFilePath);
//      return File(imageFileStream, "image/jpeg");
   
      
      
      
  }

  ngOnInit() {
  }
  
  selectedImage;
  
  setSelectedImage(image){
     this.selectedImage= image;    
  }

}



