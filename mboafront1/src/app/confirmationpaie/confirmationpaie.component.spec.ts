import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationpaieComponent } from './confirmationpaie.component';

describe('ConfirmationpaieComponent', () => {
  let component: ConfirmationpaieComponent;
  let fixture: ComponentFixture<ConfirmationpaieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfirmationpaieComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConfirmationpaieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
