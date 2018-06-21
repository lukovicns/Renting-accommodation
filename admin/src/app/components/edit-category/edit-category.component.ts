import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css'],
  animations: [fadeIn()]
})
export class EditCategoryComponent implements OnInit {

  private errorMessage: string;
  private categoryId: Number;
  private category = {};

  constructor(
    private categoryService: CategoryService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  categoryForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.categoryId = parseInt(this.route.snapshot.params['id']);
    this.categoryService.getCategory(this.categoryId)
    .subscribe(res => {
      this.category = res;
      this.categoryForm.controls['name'].setValue(this.category['name']);
    }, err => {
      this.router.navigate(['categories']);
    })
  }

  editCategory() {
    this.categoryService.editCategory(this.categoryId, this.categoryForm.value)
    .subscribe(res => {
      this.router.navigate(['categories']);
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
