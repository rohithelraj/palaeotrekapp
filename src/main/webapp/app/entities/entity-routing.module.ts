import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from '../config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pdf-data',
        data: { pageTitle: 'palaeotrekappApp.pdfData.home.title' },
        loadChildren: () => import('./pdf-data/pdf-data.module').then(m => m.PdfDataModule),
      },
      {
        path: 'hike',
        data: { authorities: [], pageTitle: 'palaeotrekappApp.hike.home.title' },
        loadChildren: () => import('./hike/hike.module').then(m => m.HikeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
