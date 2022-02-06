export interface PaginationResult<T> {
  pageSize: number;
  page: number;
  totalPage: number;
  total: number;
  results: Array<T>;
}

export interface Picture{
  id?: number;
  file: File;
  buffer: string | ArrayBuffer | null;
}

export interface ProgressUpload{
  value: number;
  filename: string;
}

export type ProgressAction = (item: ProgressUpload) => void;
