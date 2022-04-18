import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPdfData } from '../pdf-data.model';
import { DataUtils } from 'app/core/util/data-util.service';
import {PagesLoadedEvent} from "ngx-extended-pdf-viewer";

@Component({
  selector: 'jhi-pdf-data-detail',
  templateUrl: './pdf-data-detail.component.html',
})
export class PdfDataDetailComponent implements OnInit {
  public src: Blob;
  pdfData: IPdfData | null = null;


  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {
    let cc = "";
    if (this.pdfData?.pdf){
      cc = this.pdfData.pdf;
    }
    this.src = this.retrieveUrl(cc, this.pdfData?.pdfContentType);
  }

  public usePreloadedFile(base64String: string, contentType: string | null | undefined): void {
    this.src = this.retrieveUrl(base64String, contentType);
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdfData }) => {
      this.pdfData = pdfData;
    });
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
  previousState(): void {
    window.history.back();
  }
}
