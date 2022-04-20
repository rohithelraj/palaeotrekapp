import { Component, OnInit } from '@angular/core';
import {IPdfData} from "../entities/pdf-data/pdf-data.model";
import {Account} from "../core/auth/account.model";
import {DataUtils} from "../core/util/data-util.service";
import {ActivatedRoute} from "@angular/router";
import {PdfDataService} from "../entities/pdf-data/service/pdf-data.service";
import {AccountService} from "../core/auth/account.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'jhi-ydna-additional',
  templateUrl: './ydna-additional.component.html',
  styleUrls: ['./ydna-additional.component.scss']
})
export class YdnaAdditionalComponent implements OnInit {
  public src: Blob;
  pdfData: IPdfData | null = null;
  account!: Account;
  constructor(protected dataUtils: DataUtils,
              protected activatedRoute: ActivatedRoute,
              protected pdfDataService: PdfDataService,
              protected accountService: AccountService) {
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
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        const id = this.account.id;
        const pdfId = id+"_Ydna_Additional";
        this.pdfDataService.findByPdfIdAndUserId(pdfId,id).subscribe({
          next: (res: HttpResponse<IPdfData>) => {
            this.pdfData = res.body;
          },
          error: () => {
            this.pdfData = null;
          },
        })
      }
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

