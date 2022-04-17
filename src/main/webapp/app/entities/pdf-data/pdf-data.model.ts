import dayjs from 'dayjs/esm';
import { DnaType } from 'app/entities/enumerations/dna-type.model';
import { DetailedType } from 'app/entities/enumerations/detailed-type.model';

export interface IPdfData {
  id?: number;
  dnaType?: DnaType;
  detailedType?: DetailedType;
  pdfId?: string;
  dateOfCreation?: dayjs.Dayjs;
  userId?: string;
}

export class PdfData implements IPdfData {
  constructor(
    public id?: number,
    public dnaType?: DnaType,
    public detailedType?: DetailedType,
    public pdfId?: string,
    public dateOfCreation?: dayjs.Dayjs,
    public userId?: string
  ) {}
}

export function getPdfDataIdentifier(pdfData: IPdfData): number | undefined {
  return pdfData.id;
}
