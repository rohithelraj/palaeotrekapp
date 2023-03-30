import dayjs from 'dayjs/esm';

export interface IHike {
  id?: number;
  hikeDate?: dayjs.Dayjs;
  komootMap?: string;
  description1?: string | null;
  description2?: string | null;
  trainConnection?: string;
  imageData?: string | null;
  imageBlobContentType?: string | null;
  imageBlob?: string | null;
}

export class Hike implements IHike {
  constructor(
    public id?: number,
    public hikeDate?: dayjs.Dayjs,
    public komootMap?: string,
    public description1?: string | null,
    public description2?: string | null,
    public trainConnection?: string,
    public imageData?: string | null,
    public imageBlobContentType?: string | null,
    public imageBlob?: string | null
  ) {}
}

export function getHikeIdentifier(hike: IHike): number | undefined {
  return hike.id;
}
