import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PdfDataDetailComponent } from './pdf-data-detail.component';

describe('PdfData Management Detail Component', () => {
  let comp: PdfDataDetailComponent;
  let fixture: ComponentFixture<PdfDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PdfDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pdfData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PdfDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PdfDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pdfData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pdfData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
