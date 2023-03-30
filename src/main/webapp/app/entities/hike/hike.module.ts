import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HikeComponent } from './list/hike.component';
import { HikeDetailComponent } from './detail/hike-detail.component';
import { HikeUpdateComponent } from './update/hike-update.component';
import { HikeDeleteDialogComponent } from './delete/hike-delete-dialog.component';
import { HikeRoutingModule } from './route/hike-routing.module';

@NgModule({
  imports: [SharedModule, HikeRoutingModule],
  declarations: [HikeComponent, HikeDetailComponent, HikeUpdateComponent, HikeDeleteDialogComponent],
  entryComponents: [HikeDeleteDialogComponent],
})
export class HikeModule {}
