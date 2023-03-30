import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHike, Hike } from '../hike.model';
import { HikeService } from '../service/hike.service';

@Injectable({ providedIn: 'root' })
export class HikeRoutingResolveService implements Resolve<IHike> {
  constructor(protected service: HikeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHike> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hike: HttpResponse<Hike>) => {
          if (hike.body) {
            return of(hike.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hike());
  }
}
