import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HikeService } from '../service/hike.service';
import { IHike, Hike } from '../hike.model';

import { HikeUpdateComponent } from './hike-update.component';

describe('Hike Management Update Component', () => {
  let comp: HikeUpdateComponent;
  let fixture: ComponentFixture<HikeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hikeService: HikeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HikeUpdateComponent],
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
      .overrideTemplate(HikeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HikeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hikeService = TestBed.inject(HikeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const hike: IHike = { id: 456 };

      activatedRoute.data = of({ hike });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(hike));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hike>>();
      const hike = { id: 123 };
      jest.spyOn(hikeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hike }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(hikeService.update).toHaveBeenCalledWith(hike);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hike>>();
      const hike = new Hike();
      jest.spyOn(hikeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hike }));
      saveSubject.complete();

      // THEN
      expect(hikeService.create).toHaveBeenCalledWith(hike);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Hike>>();
      const hike = { id: 123 };
      jest.spyOn(hikeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hike });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hikeService.update).toHaveBeenCalledWith(hike);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
