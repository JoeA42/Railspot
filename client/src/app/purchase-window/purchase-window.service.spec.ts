import { TestBed } from '@angular/core/testing';

import { PurchaseWindowService } from './purchase-window.service';

describe('PurchaseWindowService', () => {
  let service: PurchaseWindowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PurchaseWindowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
