import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AdditionalServicesService } from '../../services/additional-services.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-additional-service',
  templateUrl: './edit-additional-service.component.html',
  styleUrls: ['./edit-additional-service.component.css'],
  animations: [fadeIn()]
})
export class EditAdditionalServiceComponent implements OnInit {

  private additionalServiceId;
  private additionalService = {};
  private errorMessage: boolean;

  constructor(
    private additionalServicesService: AdditionalServicesService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  serviceForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.additionalServiceId = parseInt(this.route.snapshot.params['id']);
    this.additionalServicesService.getAdditionalService(this.additionalServiceId)
    .subscribe(res => {
      this.additionalService = res;
      this.serviceForm.controls['name'].setValue(this.additionalService['name']);
    }, err => {
      this.router.navigate(['additional-services']);
    })
  }

  editAdditionalService() {
    this.additionalServicesService.editAdditionalService(this.additionalServiceId, this.serviceForm.value)
    .subscribe(res => {
      this.router.navigate(['additional-services']);
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
