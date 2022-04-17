import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pdf-data',
        data: { pageTitle: 'palaeotrekappApp.pdfData.home.title' },
        loadChildren: () => import('./pdf-data/pdf-data.module').then(m => m.PdfDataModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
