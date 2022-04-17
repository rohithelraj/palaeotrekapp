import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPdfData, PdfData } from '../pdf-data.model';
import { PdfDataService } from '../service/pdf-data.service';

@Injectable({ providedIn: 'root' })
export class PdfDataRoutingResolveService implements Resolve<IPdfData> {
  constructor(protected service: PdfDataService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPdfData> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pdfData: HttpResponse<PdfData>) => {
          if (pdfData.body) {
            return of(pdfData.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PdfData());
  }
}
