import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { BaseApiService } from "../base/baseapi.service";
import { Assignment, AssignmentForm, AssignmentFormUpdate } from "./assignment.type";

@Injectable({
  providedIn: "root",
})

export class AssignmentService extends BaseApiService {
  constructor( http: HttpClient, toast: ToastrService) {
      super(http, toast);
  }

  public async getAssignmentAsync(): Promise<Array<Assignment>> {
    try {
      return await this.http.get<Array<Assignment>>(`${environment.apiBaseUrl}/assignments`).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display assignments","Assignment");
      //console.log(error)
    }
    return [];
  }

  public async getAssignmentByIdAsync(id: number): Promise<Assignment | null> {
    try {
      return await this.http.get<Assignment>(`${environment.apiBaseUrl}/assignments/`+ id).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display the assignment","Assignment");
    }
    return null;
  }


  public getAssignmentsOfCourse(id: number): Observable<Array<Assignment>> {
    return this.tryGet<Array<Assignment>>(`/assignments/course/${id}`);
  }


  public async createAssignmentAsync(form: AssignmentForm): Promise<Assignment | null>{
    console.log(form)
    try{
      return await this.http.post<Assignment>(`${environment.apiBaseUrl}/assignments/`, form).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't create the assignment","Assignment");
    }
    return null;
  }

  public async deleteAssignmentById(id: number): Promise<void>{
    try{
      await this.http.delete<Assignment>(`${environment.apiBaseUrl}/assignments/` + id).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't delete the assignment","Assignment");
    }
  }

  public async updateAssignment(form: AssignmentFormUpdate): Promise<Assignment | null>{
    try{
      return await this.http.post<Assignment>(`${environment.apiBaseUrl}/assignments/`, form).toPromise();
    }catch(error){
      this.toast.error("Couldn't edit the assignment","Assignment");
    }
    return null;
  }

  public getAssignmentById(id: number): Observable<Assignment> {
    return this.tryGet<Assignment>(`/assignments/${id}`);
  }
}
