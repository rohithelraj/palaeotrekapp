import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPdfData, PdfData } from '../pdf-data.model';
import { PdfDataService } from '../service/pdf-data.service';
import { DnaType } from 'app/entities/enumerations/dna-type.model';
import { DetailedType } from 'app/entities/enumerations/detailed-type.model';

@Component({
  selector: 'jhi-pdf-data-update',
  templateUrl: './pdf-data-update.component.html',
})
export class PdfDataUpdateComponent implements OnInit {
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
  });

  constructor(protected pdfDataService: PdfDataService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdfData }) => {
      this.updateForm(pdfData);
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
    };
  }
}
