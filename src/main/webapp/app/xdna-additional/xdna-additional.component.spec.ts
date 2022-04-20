import { ComponentFixture, TestBed } from '@angular/core/testing';

import { XdnaAdditionalComponent } from './xdna-additional.component';

describe('XdnaAdditionalComponent', () => {
  let component: XdnaAdditionalComponent;
  let fixture: ComponentFixture<XdnaAdditionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ XdnaAdditionalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(XdnaAdditionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
