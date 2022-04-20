import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MtdnaAdditionalComponent } from './mtdna-additional.component';

describe('MtdnaAdditionalComponent', () => {
  let component: MtdnaAdditionalComponent;
  let fixture: ComponentFixture<MtdnaAdditionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MtdnaAdditionalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MtdnaAdditionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
