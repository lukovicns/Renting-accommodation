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
      this.typeForm.reset();
    }, err => {
      console.log(err);
    })
  }

  removeType(typeId) {
    this.typeService.removeType(typeId)
    .subscribe(res => {
      this.initTypes();
    }, err => {
      console.log(err);
    })
  }
}
