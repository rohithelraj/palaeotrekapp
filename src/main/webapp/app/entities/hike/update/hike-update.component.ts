import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHike, Hike } from '../hike.model';
import { HikeService } from '../service/hike.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-hike-update',
  templateUrl: './hike-update.component.html',
})
export class HikeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    hikeDate: [null, [Validators.required]],
    komootMap: [null, [Validators.required]],
    description1: [],
    description2: [],
    trainConnection: [null, [Validators.required]],
    imageData: [],
    imageBlob: [],
    imageBlobContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected hikeService: HikeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hike }) => {
      this.updateForm(hike);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('palaeotrekappApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hike = this.createFromForm();
    if (hike.id !== undefined) {
      this.subscribeToSaveResponse(this.hikeService.update(hike));
    } else {
      this.subscribeToSaveResponse(this.hikeService.create(hike));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHike>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(hike: IHike): void {
    this.editForm.patchValue({
      id: hike.id,
      hikeDate: hike.hikeDate,
      komootMap: hike.komootMap,
      description1: hike.description1,
      description2: hike.description2,
      trainConnection: hike.trainConnection,
      imageData: hike.imageData,
      imageBlob: hike.imageBlob,
      imageBlobContentType: hike.imageBlobContentType,
    });
  }

  protected createFromForm(): IHike {
    return {
      ...new Hike(),
      id: this.editForm.get(['id'])!.value,
      hikeDate: this.editForm.get(['hikeDate'])!.value,
      komootMap: this.editForm.get(['komootMap'])!.value,
      description1: this.editForm.get(['description1'])!.value,
      description2: this.editForm.get(['description2'])!.value,
      trainConnection: this.editForm.get(['trainConnection'])!.value,
      imageData: this.editForm.get(['imageData'])!.value,
      imageBlobContentType: this.editForm.get(['imageBlobContentType'])!.value,
      imageBlob: this.editForm.get(['imageBlob'])!.value,
    };
  }
}
