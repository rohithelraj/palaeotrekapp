import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PdfDataService } from '../service/pdf-data.service';
import { IPdfData, PdfData } from '../pdf-data.model';

import { PdfDataUpdateComponent } from './pdf-data-update.component';

describe('PdfData Management Update Component', () => {
  let comp: PdfDataUpdateComponent;
  let fixture: ComponentFixture<PdfDataUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pdfDataService: PdfDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PdfDataUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PdfDataUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PdfDataUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pdfDataService = TestBed.inject(PdfDataService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pdfData: IPdfData = { id: 456 };

      activatedRoute.data = of({ pdfData });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pdfData));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PdfData>>();
      const pdfData = { id: 123 };
      jest.spyOn(pdfDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pdfData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pdfData }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pdfDataService.update).toHaveBeenCalledWith(pdfData);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PdfData>>();
      const pdfData = new PdfData();
      jest.spyOn(pdfDataService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pdfData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pdfData }));
      saveSubject.complete();

      // THEN
      expect(pdfDataService.create).toHaveBeenCalledWith(pdfData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PdfData>>();
      const pdfData = { id: 123 };
      jest.spyOn(pdfDataService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pdfData });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pdfDataService.update).toHaveBeenCalledWith(pdfData);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
