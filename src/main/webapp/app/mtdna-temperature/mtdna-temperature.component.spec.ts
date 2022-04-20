import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MtdnaTemperatureComponent } from './mtdna-temperature.component';

describe('MtdnaTemperatureComponent', () => {
  let component: MtdnaTemperatureComponent;
  let fixture: ComponentFixture<MtdnaTemperatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MtdnaTemperatureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MtdnaTemperatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
