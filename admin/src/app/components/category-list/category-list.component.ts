import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { CategoryService } from '../../services/category.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css'],
  animations: [fadeIn()]
})
export class CategoryListComponent implements OnInit {

  private categories = [];
  private errorMessage: string;

  constructor(
    private categoryService: CategoryService,
    private formBuilder: FormBuilder
  ) { }

  categoryForm = this.formBuilder.group({
    name: ['', Validators.required]
  })

  ngOnInit() {
    this.categoryService.getCategories()
    .subscribe(res => this.categories = res);
  }

  initCategories() {
    this.categoryService.getCategories()
    .subscribe(res => this.categories = res);
  }

  addCategory() {
    this.categoryService.addCategory(this.categoryForm.value)
    .subscribe(res => {
      this.initCategories();
    }, err => {
      this.errorMessage = err['error'];
    })
    this.categoryForm.reset();
  }

  activateCategory(categoryId) {
    this.categoryService.activateCategory(categoryId)
    .subscribe(res => {
      this.initCategories();
    }, err => {
      this.errorMessage = err['error'];
    })
  }

  deactivateCategory(categoryId) {
    this.categoryService.deactivateCategory(categoryId)
    .subscribe(res => {
      this.initCategories();
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
