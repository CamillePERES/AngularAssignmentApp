export interface PaginationResult<T> {
  pageSize: number;
  page: number;
  totalPage: number;
  total: number;
  results: Array<T>;
}

