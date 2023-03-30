import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHike, getHikeIdentifier } from '../hike.model';

export type EntityResponseType = HttpResponse<IHike>;
export type EntityArrayResponseType = HttpResponse<IHike[]>;

@Injectable({ providedIn: 'root' })
export class HikeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hikes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(hike: IHike): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hike);
    return this.http
      .post<IHike>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hike: IHike): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hike);
    return this.http
      .put<IHike>(`${this.resourceUrl}/${getHikeIdentifier(hike) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hike: IHike): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hike);
    return this.http
      .patch<IHike>(`${this.resourceUrl}/${getHikeIdentifier(hike) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHike>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHike[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHikeToCollectionIfMissing(hikeCollection: IHike[], ...hikesToCheck: (IHike | null | undefined)[]): IHike[] {
    const hikes: IHike[] = hikesToCheck.filter(isPresent);
    if (hikes.length > 0) {
      const hikeCollectionIdentifiers = hikeCollection.map(hikeItem => getHikeIdentifier(hikeItem)!);
      const hikesToAdd = hikes.filter(hikeItem => {
        const hikeIdentifier = getHikeIdentifier(hikeItem);
        if (hikeIdentifier == null || hikeCollectionIdentifiers.includes(hikeIdentifier)) {
          return false;
        }
        hikeCollectionIdentifiers.push(hikeIdentifier);
        return true;
      });
      return [...hikesToAdd, ...hikeCollection];
    }
    return hikeCollection;
  }

  protected convertDateFromClient(hike: IHike): IHike {
    return Object.assign({}, hike, {
      hikeDate: hike.hikeDate?.isValid() ? hike.hikeDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hikeDate = res.body.hikeDate ? dayjs(res.body.hikeDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hike: IHike) => {
        hike.hikeDate = hike.hikeDate ? dayjs(hike.hikeDate) : undefined;
      });
    }
    return res;
  }
}
