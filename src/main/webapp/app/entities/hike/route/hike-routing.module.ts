import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HikeComponent } from '../list/hike.component';
import { HikeDetailComponent } from '../detail/hike-detail.component';
import { HikeUpdateComponent } from '../update/hike-update.component';
import { HikeRoutingResolveService } from './hike-routing-resolve.service';

const hikeRoute: Routes = [
  {
    path: '',
    component: HikeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HikeDetailComponent,
    resolve: {
      hike: HikeRoutingResolveService,
    },
    //canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HikeUpdateComponent,
    resolve: {
      hike: HikeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HikeUpdateComponent,
    resolve: {
      hike: HikeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hikeRoute)],
  exports: [RouterModule],
})
export class HikeRoutingModule {}
