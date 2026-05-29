import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavoritesDistComponent } from './favorites-dist.component';

describe('FavoritesDistComponent', () => {
  let component: FavoritesDistComponent;
  let fixture: ComponentFixture<FavoritesDistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavoritesDistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FavoritesDistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
