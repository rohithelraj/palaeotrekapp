import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YdnaAdditionalComponent } from './ydna-additional.component';

describe('YdnaAdditionalComponent', () => {
  let component: YdnaAdditionalComponent;
  let fixture: ComponentFixture<YdnaAdditionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YdnaAdditionalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(YdnaAdditionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
