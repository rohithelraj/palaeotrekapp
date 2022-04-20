import { ComponentFixture, TestBed } from '@angular/core/testing';

import { XdnaTemperatureComponent } from './xdna-temperature.component';

describe('XdnaTemperatureComponent', () => {
  let component: XdnaTemperatureComponent;
  let fixture: ComponentFixture<XdnaTemperatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ XdnaTemperatureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(XdnaTemperatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
