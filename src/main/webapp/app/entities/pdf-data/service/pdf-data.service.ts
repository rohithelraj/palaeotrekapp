import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPdfData, getPdfDataIdentifier } from '../pdf-data.model';

export type EntityResponseType = HttpResponse<IPdfData>;
export type EntityArrayResponseType = HttpResponse<IPdfData[]>;

@Injectable({ providedIn: 'root' })
export class PdfDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pdf-data');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pdfData: IPdfData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pdfData);
    return this.http
      .post<IPdfData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pdfData: IPdfData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pdfData);
    return this.http
      .put<IPdfData>(`${this.resourceUrl}/${getPdfDataIdentifier(pdfData) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(pdfData: IPdfData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pdfData);
    return this.http
      .patch<IPdfData>(`${this.resourceUrl}/${getPdfDataIdentifier(pdfData) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPdfData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPdfData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPdfDataToCollectionIfMissing(pdfDataCollection: IPdfData[], ...pdfDataToCheck: (IPdfData | null | undefined)[]): IPdfData[] {
    const pdfData: IPdfData[] = pdfDataToCheck.filter(isPresent);
    if (pdfData.length > 0) {
      const pdfDataCollectionIdentifiers = pdfDataCollection.map(pdfDataItem => getPdfDataIdentifier(pdfDataItem)!);
      const pdfDataToAdd = pdfData.filter(pdfDataItem => {
        const pdfDataIdentifier = getPdfDataIdentifier(pdfDataItem);
        if (pdfDataIdentifier == null || pdfDataCollectionIdentifiers.includes(pdfDataIdentifier)) {
          return false;
        }
        pdfDataCollectionIdentifiers.push(pdfDataIdentifier);
        return true;
      });
      return [...pdfDataToAdd, ...pdfDataCollection];
    }
    return pdfDataCollection;
  }

  protected convertDateFromClient(pdfData: IPdfData): IPdfData {
    return Object.assign({}, pdfData, {
      dateOfCreation: pdfData.dateOfCreation?.isValid() ? pdfData.dateOfCreation.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfCreation = res.body.dateOfCreation ? dayjs(res.body.dateOfCreation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pdfData: IPdfData) => {
        pdfData.dateOfCreation = pdfData.dateOfCreation ? dayjs(pdfData.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
