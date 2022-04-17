import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPdfData } from '../pdf-data.model';
import { PdfDataService } from '../service/pdf-data.service';

@Component({
  templateUrl: './pdf-data-delete-dialog.component.html',
})
export class PdfDataDeleteDialogComponent {
  pdfData?: IPdfData;

  constructor(protected pdfDataService: PdfDataService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pdfDataService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
