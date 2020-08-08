import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseWindowComponent } from './purchase-window.component';

describe('PurchaseWindowComponent', () => {
  let component: PurchaseWindowComponent;
  let fixture: ComponentFixture<PurchaseWindowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PurchaseWindowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
