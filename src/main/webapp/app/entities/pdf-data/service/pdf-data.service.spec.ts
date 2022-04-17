import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { DnaType } from 'app/entities/enumerations/dna-type.model';
import { DetailedType } from 'app/entities/enumerations/detailed-type.model';
import { IPdfData, PdfData } from '../pdf-data.model';

import { PdfDataService } from './pdf-data.service';

describe('PdfData Service', () => {
  let service: PdfDataService;
  let httpMock: HttpTestingController;
  let elemDefault: IPdfData;
  let expectedResult: IPdfData | IPdfData[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PdfDataService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      dnaType: DnaType.MTDNA,
      detailedType: DetailedType.MAPS,
      pdfId: 'AAAAAAA',
      dateOfCreation: currentDate,
      userId: 'AAAAAAA',
      pdfContentType: 'image/png',
      pdf: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateOfCreation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PdfData', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateOfCreation: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfCreation: currentDate,
        },
        returnedFromService
      );

      service.create(new PdfData()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PdfData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dnaType: 'BBBBBB',
          detailedType: 'BBBBBB',
          pdfId: 'BBBBBB',
          dateOfCreation: currentDate.format(DATE_FORMAT),
          userId: 'BBBBBB',
          pdf: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfCreation: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PdfData', () => {
      const patchObject = Object.assign(
        {
          pdfId: 'BBBBBB',
          userId: 'BBBBBB',
        },
        new PdfData()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateOfCreation: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PdfData', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dnaType: 'BBBBBB',
          detailedType: 'BBBBBB',
          pdfId: 'BBBBBB',
          dateOfCreation: currentDate.format(DATE_FORMAT),
          userId: 'BBBBBB',
          pdf: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateOfCreation: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PdfData', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPdfDataToCollectionIfMissing', () => {
      it('should add a PdfData to an empty array', () => {
        const pdfData: IPdfData = { id: 123 };
        expectedResult = service.addPdfDataToCollectionIfMissing([], pdfData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pdfData);
      });

      it('should not add a PdfData to an array that contains it', () => {
        const pdfData: IPdfData = { id: 123 };
        const pdfDataCollection: IPdfData[] = [
          {
            ...pdfData,
          },
          { id: 456 },
        ];
        expectedResult = service.addPdfDataToCollectionIfMissing(pdfDataCollection, pdfData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PdfData to an array that doesn't contain it", () => {
        const pdfData: IPdfData = { id: 123 };
        const pdfDataCollection: IPdfData[] = [{ id: 456 }];
        expectedResult = service.addPdfDataToCollectionIfMissing(pdfDataCollection, pdfData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pdfData);
      });

      it('should add only unique PdfData to an array', () => {
        const pdfDataArray: IPdfData[] = [{ id: 123 }, { id: 456 }, { id: 58110 }];
        const pdfDataCollection: IPdfData[] = [{ id: 123 }];
        expectedResult = service.addPdfDataToCollectionIfMissing(pdfDataCollection, ...pdfDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pdfData: IPdfData = { id: 123 };
        const pdfData2: IPdfData = { id: 456 };
        expectedResult = service.addPdfDataToCollectionIfMissing([], pdfData, pdfData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pdfData);
        expect(expectedResult).toContain(pdfData2);
      });

      it('should accept null and undefined values', () => {
        const pdfData: IPdfData = { id: 123 };
        expectedResult = service.addPdfDataToCollectionIfMissing([], null, pdfData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pdfData);
      });

      it('should return initial array if no PdfData is added', () => {
        const pdfDataCollection: IPdfData[] = [{ id: 123 }];
        expectedResult = service.addPdfDataToCollectionIfMissing(pdfDataCollection, undefined, null);
        expect(expectedResult).toEqual(pdfDataCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
