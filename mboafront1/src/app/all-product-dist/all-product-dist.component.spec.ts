import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllProductDistComponent } from './all-product-dist.component';

describe('AllProductDistComponent', () => {
  let component: AllProductDistComponent;
  let fixture: ComponentFixture<AllProductDistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllProductDistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllProductDistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
