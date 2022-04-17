import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PdfDataComponent } from '../list/pdf-data.component';
import { PdfDataDetailComponent } from '../detail/pdf-data-detail.component';
import { PdfDataUpdateComponent } from '../update/pdf-data-update.component';
import { PdfDataRoutingResolveService } from './pdf-data-routing-resolve.service';

const pdfDataRoute: Routes = [
  {
    path: '',
    component: PdfDataComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PdfDataDetailComponent,
    resolve: {
      pdfData: PdfDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PdfDataUpdateComponent,
    resolve: {
      pdfData: PdfDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PdfDataUpdateComponent,
    resolve: {
      pdfData: PdfDataRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pdfDataRoute)],
  exports: [RouterModule],
})
export class PdfDataRoutingModule {}
