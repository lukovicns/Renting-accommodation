import { TestBed, inject } from '@angular/core/testing';
import { HttpInterceptorService } from './httpinterceptor.service';

describe('HttpinterceptorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpInterceptorService]
    });
  });

  it('should be created', inject([HttpInterceptorService], (service: HttpInterceptorService) => {
    expect(service).toBeTruthy();
  }));
});
