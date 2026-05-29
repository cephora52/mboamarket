import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandeDistributeurComponent } from './commande-distributeur.component';

describe('CommandeDistributeurComponent', () => {
  let component: CommandeDistributeurComponent;
  let fixture: ComponentFixture<CommandeDistributeurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommandeDistributeurComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CommandeDistributeurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
