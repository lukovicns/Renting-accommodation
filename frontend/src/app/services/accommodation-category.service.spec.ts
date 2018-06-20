import { TestBed, inject } from '@angular/core/testing';

import { AccommodationCategoryService } from './accommodation-category.service';

describe('AccommodationCategoryService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccommodationCategoryService]
    });
  });

  it('should be created', inject([AccommodationCategoryService], (service: AccommodationCategoryService) => {
    expect(service).toBeTruthy();
  }));
});
