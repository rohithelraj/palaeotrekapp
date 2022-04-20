import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YdnaMapsComponent } from './ydna-maps.component';

describe('YdnaMapsComponent', () => {
  let component: YdnaMapsComponent;
  let fixture: ComponentFixture<YdnaMapsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YdnaMapsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(YdnaMapsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
