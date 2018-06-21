import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators } from '@angular/forms';
import { TypeService } from '../../services/type.service';

@Component({
  selector: 'app-type-list',
  templateUrl: './type-list.component.html',
  styleUrls: ['./type-list.component.css'],
  animations: [fadeIn()]
})
export class TypeListComponent implements OnInit {

  private types = [];
  private errorMessage: string;

  constructor(
    private formBuilder: FormBuilder,
    private typeService: TypeService
  ) { }

  typeForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.typeService.getTypes()
    .subscribe(res => this.types = res);
  }

  initTypes() {
    this.typeService.getTypes()
    .subscribe(res => this.types = res);
  }

  addType() {
    this.typeService.addType(this.typeForm.value)
    .subscribe(res => {
      this.initTypes();
    }, err => {
      this.errorMessage = err['error'];
    })
    this.typeForm.reset();
  }

  activateType(typeId) {
    this.typeService.activateType(typeId)
    .subscribe(res => {
      this.initTypes();
    }, err => {
      this.errorMessage = err['error'];
    })
  }

  deactivateType(typeId) {
    this.typeService.deactivateType(typeId)
    .subscribe(res => {
      this.initTypes();
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
