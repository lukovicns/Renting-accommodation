import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import {ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import { SecurityContext } from "@angular/core";

@Component({
  selector: 'app-apartment-table',
  templateUrl: './apartment-table.component.html',
  styleUrls: ['./apartment-table.component.css'],
  animations: [fadeIn()]
})
export class ApartmentTableComponent implements OnInit {
    images;
  constructor(private apartmentService : ApartmentService, private formBuilder: FormBuilder, private router: Router, private dom: DomSanitizer) {
//      <img [src]="_DomSanitizationService.bypassSecurityTrustUrl(imgSrcProperty)"/>
      
      this.images = dom.bypassSecurityTrustUrl("../assets/out29.png");
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
