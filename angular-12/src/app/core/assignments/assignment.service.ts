import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { Assignment, AssignmentForm } from "./assignment.type";

@Injectable({
  providedIn: "root",
})
export class AssignmentService {
  constructor(private http: HttpClient, protected toast: ToastrService) {}

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

  public async createAssignmentAsync(form: AssignmentForm): Promise<Assignment | null>{
    try{
      await this.http.post<Assignment>(`${environment.apiBaseUrl}/assignments/`, form).toPromise();
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
}
