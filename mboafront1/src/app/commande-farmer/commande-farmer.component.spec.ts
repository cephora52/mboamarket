import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandeFarmerComponent } from './commande-farmer.component';

describe('CommandeFarmerComponent', () => {
  let component: CommandeFarmerComponent;
  let fixture: ComponentFixture<CommandeFarmerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommandeFarmerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CommandeFarmerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
