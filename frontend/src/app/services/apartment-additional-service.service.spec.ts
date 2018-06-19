import { TestBed, inject } from '@angular/core/testing';

import { ApartmentAdditionalServiceService } from './apartment-additional-service.service';

describe('ApartmentAdditionalServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApartmentAdditionalServiceService]
    });
  });

  it('should be created', inject([ApartmentAdditionalServiceService], (service: ApartmentAdditionalServiceService) => {
    expect(service).toBeTruthy();
  }));
});
