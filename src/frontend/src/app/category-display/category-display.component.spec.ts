import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryDisplayComponent } from './category-display.component';

describe('CategoryDisplayComponent', () => {
  let component: CategoryDisplayComponent;
  let fixture: ComponentFixture<CategoryDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryDisplayComponent]
    });
    fixture = TestBed.createComponent(CategoryDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
