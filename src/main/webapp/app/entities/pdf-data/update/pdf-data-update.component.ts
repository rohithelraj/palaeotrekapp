import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPdfData, PdfData } from '../pdf-data.model';
import { PdfDataService } from '../service/pdf-data.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { DnaType } from 'app/entities/enumerations/dna-type.model';
import { DetailedType } from 'app/entities/enumerations/detailed-type.model';

@Component({
  selector: 'jhi-pdf-data-update',
  templateUrl: './pdf-data-update.component.html',
})
export class PdfDataUpdateComponent implements OnInit {
  public src: Blob;
  isSaving = false;
  dnaTypeValues = Object.keys(DnaType);
  detailedTypeValues = Object.keys(DetailedType);

  editForm = this.fb.group({
    id: [],
    dnaType: [null, [Validators.required]],
    detailedType: [null, [Validators.required]],
    pdfId: [null, [Validators.required]],
    dateOfCreation: [null, [Validators.required]],
    userId: [null, [Validators.required]],
    pdf: [],
    pdfContentType: [],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected pdfDataService: PdfDataService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    this.src = this.retrieveUrl(this.editForm.get('pdf')!.value, this.editForm.get('pdfContentType')!.value)
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdfData }) => {
      this.updateForm(pdfData);
    });
  }
  public usePreloadedFile(base64String: string, contentType: string | null | undefined): void {
    this.src = this.retrieveUrl(base64String, contentType);
  }
  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }
  retrieveUrl(base64String: string, contentType: string | null | undefined): Blob{
    return this.dataUtils.retrieveUrl(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('palaeotrekappApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pdfData = this.createFromForm();
    if (pdfData.id !== undefined) {
      this.subscribeToSaveResponse(this.pdfDataService.update(pdfData));
    } else {
      this.subscribeToSaveResponse(this.pdfDataService.create(pdfData));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPdfData>>): void {
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

  protected updateForm(pdfData: IPdfData): void {
    this.editForm.patchValue({
      id: pdfData.id,
      dnaType: pdfData.dnaType,
      detailedType: pdfData.detailedType,
      pdfId: pdfData.pdfId,
      dateOfCreation: pdfData.dateOfCreation,
      userId: pdfData.userId,
      pdf: pdfData.pdf,
      pdfContentType: pdfData.pdfContentType,
    });
  }

  protected createFromForm(): IPdfData {
    return {
      ...new PdfData(),
      id: this.editForm.get(['id'])!.value,
      dnaType: this.editForm.get(['dnaType'])!.value,
      detailedType: this.editForm.get(['detailedType'])!.value,
      pdfId: this.editForm.get(['pdfId'])!.value,
      dateOfCreation: this.editForm.get(['dateOfCreation'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      pdfContentType: this.editForm.get(['pdfContentType'])!.value,
      pdf: this.editForm.get(['pdf'])!.value,
    };
  }
}
