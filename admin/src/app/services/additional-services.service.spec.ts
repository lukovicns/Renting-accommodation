import { TestBed, inject } from '@angular/core/testing';

import { AdditionalServicesService } from './additional-services.service';

describe('AdditionalServicesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdditionalServicesService]
    });
  });

  it('should be created', inject([AdditionalServicesService], (service: AdditionalServicesService) => {
    expect(service).toBeTruthy();
  }));
});
