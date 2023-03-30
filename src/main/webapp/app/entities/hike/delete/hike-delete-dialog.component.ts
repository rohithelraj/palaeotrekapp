import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHike } from '../hike.model';
import { HikeService } from '../service/hike.service';

@Component({
  templateUrl: './hike-delete-dialog.component.html',
})
export class HikeDeleteDialogComponent {
  hike?: IHike;

  constructor(protected hikeService: HikeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hikeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
