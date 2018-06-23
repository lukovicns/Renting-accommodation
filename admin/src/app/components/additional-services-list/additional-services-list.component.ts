import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AdditionalServicesService } from '../../services/additional-services.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-additional-services-list',
  templateUrl: './additional-services-list.component.html',
  styleUrls: ['./additional-services-list.component.css'],
  animations: [fadeIn()]
})
export class AdditionalServicesListComponent implements OnInit {

  private services = [];
  private errorMessage: boolean;

  constructor(
    private additionalServicesService: AdditionalServicesService,
    private formBuilder: FormBuilder
  ) { }

  additionalServiceForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.additionalServicesService.getAdditionalServices()
    .subscribe(res => this.services = res);
  }

  addAdditionalService() {
    this.additionalServicesService.addAdditionalService(this.additionalServiceForm.value)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
    this.additionalServiceForm.reset();
  }

  deleteAdditionalService(additionalServiceId) {
    this.additionalServicesService.deleteAdditionalService(additionalServiceId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
