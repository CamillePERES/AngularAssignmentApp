import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export abstract class BaseApiService {

  get apiurl(): string { return environment.apiBaseUrl; }

  constructor(protected http: HttpClient, protected toast: ToastrService) {

  }

  public tryGet<T>(url: string): Observable<T> {
    return this.http.get<T>(`${this.apiurl}${url}`)
    .pipe<T>(catchError(this.handleError<T>()));
  }

  public tryPost<T,K>(url: string, data: T): Observable<K> {
      return this.http.post<K>(`${this.apiurl}${url}`, data)
      .pipe<K>(catchError(this.handleError<K>()));
  }

  public tryPut<T, K>(url: string, data: T): Observable<K> {
    return this.http.put<K>(`${this.apiurl}${url}`, data)
    .pipe<K>(catchError(this.handleError<K>()));
  }

  public async tryGetAsync<T>(url: string): Promise<T> {
    try {
      return await this.http.get<T>(`${this.apiurl}${url}`).toPromise();
    }
    catch(error: any){
      throw this.handleErrorAsync(error);
    }
  }

  public async tryPostAsync<T,K>(url: string, data: T): Promise<K> {
    try {
      return await this.http.post<K>(`${this.apiurl}${url}`, data).toPromise();
    }
    catch(error: any){
      throw this.handleErrorAsync(error);
    }
  }

  public async tryPutAsync<T, K>(url: string, data: T): Promise<K> {
    try {
      return await this.http.put<K>(`${this.apiurl}${url}`, data).toPromise();
    }
    catch(error: any){
      throw this.handleErrorAsync(error);
    }
  }

  private handleError<T>() {
    return (error: HttpErrorResponse): Observable<T> => {
      console.log(error);
      try {
          this.toast.error(`Status : ${error.status} : ${error.error.message}`);
          return throwError(() => error);
      } catch(errorParse){
          return throwError(() => error);
      }
    };
  }

  private handleErrorAsync(error: any): Error {
    console.error(error);
    if(error instanceof HttpErrorResponse){
      this.toast.error(`Status : ${error.status} : ${error.error.message}`);
    }
    else {
      this.toast.error(`Status : ${error.status} : ${error.error}`);
    }
    return error;
  }
}



