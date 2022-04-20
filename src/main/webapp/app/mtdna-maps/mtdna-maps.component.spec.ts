import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MtdnaMapsComponent } from './mtdna-maps.component';

describe('MtdnaMapsComponent', () => {
  let component: MtdnaMapsComponent;
  let fixture: ComponentFixture<MtdnaMapsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MtdnaMapsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MtdnaMapsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
