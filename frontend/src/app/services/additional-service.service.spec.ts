import { TestBed, inject } from '@angular/core/testing';

import { AdditionalServiceService } from './additional-service.service';

describe('AdditionalServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdditionalServiceService]
    });
  });

  it('should be created', inject([AdditionalServiceService], (service: AdditionalServiceService) => {
    expect(service).toBeTruthy();
  }));
});
