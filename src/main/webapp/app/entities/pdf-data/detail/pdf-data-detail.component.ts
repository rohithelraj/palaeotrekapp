import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPdfData } from '../pdf-data.model';

@Component({
  selector: 'jhi-pdf-data-detail',
  templateUrl: './pdf-data-detail.component.html',
})
export class PdfDataDetailComponent implements OnInit {
  pdfData: IPdfData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdfData }) => {
      this.pdfData = pdfData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
