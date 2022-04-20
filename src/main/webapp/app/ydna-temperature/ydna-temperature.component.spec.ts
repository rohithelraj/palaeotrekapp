import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YdnaTemperatureComponent } from './ydna-temperature.component';

describe('YdnaTemperatureComponent', () => {
  let component: YdnaTemperatureComponent;
  let fixture: ComponentFixture<YdnaTemperatureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YdnaTemperatureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(YdnaTemperatureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
