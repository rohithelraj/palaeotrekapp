import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PdfDataComponent } from './list/pdf-data.component';
import { PdfDataDetailComponent } from './detail/pdf-data-detail.component';
import { PdfDataUpdateComponent } from './update/pdf-data-update.component';
import { PdfDataDeleteDialogComponent } from './delete/pdf-data-delete-dialog.component';
import { PdfDataRoutingModule } from './route/pdf-data-routing.module';
import {NgxExtendedPdfViewerModule} from "ngx-extended-pdf-viewer";

@NgModule({
  imports: [SharedModule, PdfDataRoutingModule,NgxExtendedPdfViewerModule],
  declarations: [PdfDataComponent, PdfDataDetailComponent, PdfDataUpdateComponent, PdfDataDeleteDialogComponent],
  entryComponents: [PdfDataDeleteDialogComponent],
})
export class PdfDataModule {}
