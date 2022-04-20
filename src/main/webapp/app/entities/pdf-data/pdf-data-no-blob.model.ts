import dayjs from 'dayjs/esm';
import { DnaType } from 'app/entities/enumerations/dna-type.model';
import { DetailedType } from 'app/entities/enumerations/detailed-type.model';

export interface IPdfDataNoBlob {
  id?: number;
  dnaType?: DnaType;
  detailedType?: DetailedType;
  pdfId?: string;
  dateOfCreation?: dayjs.Dayjs;
  userId?: string;
  pdfContentType?: string | null;
}

export class PdfDataNoBlob implements IPdfDataNoBlob {
  constructor(
    public id?: number,
    public dnaType?: DnaType,
    public detailedType?: DetailedType,
    public pdfId?: string,
    public dateOfCreation?: dayjs.Dayjs,
    public userId?: string,
    public pdfContentType?: string | null
  ) {}
}

export function getPdfDataIdentifier(pdfData: IPdfDataNoBlob): number | undefined {
  return pdfData.id;
}
