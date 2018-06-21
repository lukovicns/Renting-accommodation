import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { TypeService } from '../../services/type.service';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-type',
  templateUrl: './edit-type.component.html',
  styleUrls: ['./edit-type.component.css'],
  animations: [fadeIn()]
})
export class EditTypeComponent implements OnInit {

  private errorMessage: string;
  private typeId: Number;
  private type = {};

  constructor(
    private typeService: TypeService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  typeForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.typeId = parseInt(this.route.snapshot.params['id']);
    this.typeService.getType(this.typeId)
    .subscribe(res => {
      this.type = res;
      this.typeForm.controls['name'].setValue(this.type['name']);
    }, err => {
      this.router.navigate(['types']);
    })
  }

  editType() {
    this.typeService.editType(this.typeId, this.typeForm.value)
    .subscribe(res => {
      this.router.navigate(['types']);
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
