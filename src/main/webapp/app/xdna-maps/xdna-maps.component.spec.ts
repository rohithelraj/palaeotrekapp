import { ComponentFixture, TestBed } from '@angular/core/testing';

import { XdnaMapsComponent } from './xdna-maps.component';

describe('XdnaMapsComponent', () => {
  let component: XdnaMapsComponent;
  let fixture: ComponentFixture<XdnaMapsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ XdnaMapsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(XdnaMapsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
