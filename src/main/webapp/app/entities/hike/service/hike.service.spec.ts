import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IHike, Hike } from '../hike.model';

import { HikeService } from './hike.service';

describe('Hike Service', () => {
  let service: HikeService;
  let httpMock: HttpTestingController;
  let elemDefault: IHike;
  let expectedResult: IHike | IHike[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HikeService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      hikeDate: currentDate,
      komootMap: 'AAAAAAA',
      description1: 'AAAAAAA',
      description2: 'AAAAAAA',
      trainConnection: 'AAAAAAA',
      imageData: 'AAAAAAA',
      imageBlobContentType: 'image/png',
      imageBlob: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          hikeDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Hike', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          hikeDate: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hikeDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Hike()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Hike', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hikeDate: currentDate.format(DATE_FORMAT),
          komootMap: 'BBBBBB',
          description1: 'BBBBBB',
          description2: 'BBBBBB',
          trainConnection: 'BBBBBB',
          imageData: 'BBBBBB',
          imageBlob: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hikeDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Hike', () => {
      const patchObject = Object.assign(
        {
          description1: 'BBBBBB',
          trainConnection: 'BBBBBB',
        },
        new Hike()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          hikeDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Hike', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          hikeDate: currentDate.format(DATE_FORMAT),
          komootMap: 'BBBBBB',
          description1: 'BBBBBB',
          description2: 'BBBBBB',
          trainConnection: 'BBBBBB',
          imageData: 'BBBBBB',
          imageBlob: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hikeDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Hike', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addHikeToCollectionIfMissing', () => {
      it('should add a Hike to an empty array', () => {
        const hike: IHike = { id: 123 };
        expectedResult = service.addHikeToCollectionIfMissing([], hike);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hike);
      });

      it('should not add a Hike to an array that contains it', () => {
        const hike: IHike = { id: 123 };
        const hikeCollection: IHike[] = [
          {
            ...hike,
          },
          { id: 456 },
        ];
        expectedResult = service.addHikeToCollectionIfMissing(hikeCollection, hike);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Hike to an array that doesn't contain it", () => {
        const hike: IHike = { id: 123 };
        const hikeCollection: IHike[] = [{ id: 456 }];
        expectedResult = service.addHikeToCollectionIfMissing(hikeCollection, hike);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hike);
      });

      it('should add only unique Hike to an array', () => {
        const hikeArray: IHike[] = [{ id: 123 }, { id: 456 }, { id: 40873 }];
        const hikeCollection: IHike[] = [{ id: 123 }];
        expectedResult = service.addHikeToCollectionIfMissing(hikeCollection, ...hikeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hike: IHike = { id: 123 };
        const hike2: IHike = { id: 456 };
        expectedResult = service.addHikeToCollectionIfMissing([], hike, hike2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hike);
        expect(expectedResult).toContain(hike2);
      });

      it('should accept null and undefined values', () => {
        const hike: IHike = { id: 123 };
        expectedResult = service.addHikeToCollectionIfMissing([], null, hike, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hike);
      });

      it('should return initial array if no Hike is added', () => {
        const hikeCollection: IHike[] = [{ id: 123 }];
        expectedResult = service.addHikeToCollectionIfMissing(hikeCollection, undefined, null);
        expect(expectedResult).toEqual(hikeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
