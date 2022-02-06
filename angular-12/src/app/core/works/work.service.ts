import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { BaseApiService } from "../base/baseapi.service";
import { IdentityService } from "../identity/identity.service";
import { Work, WorkCreateForm, WorkSearchForm, WorkUpdateForm } from "./work.type";

@Injectable({
  providedIn: 'root'
})
export class WorkService extends BaseApiService
{

  constructor(
    http: HttpClient,
    toast: ToastrService,
    private identityService: IdentityService
    )
  {
    super(http, toast);
  }

  public getWorkOfAssignmentById(id: number): Observable<Work | null>{
    return this.tryGet<Work | null>(`/works/assignment/${id}`);
  }

  public async createWork(form: WorkCreateForm): Promise<Work> {
    return this.tryPostAsync<WorkCreateForm, Work>(`/works`, form);
  }

  public async updateWork(form: WorkUpdateForm): Promise<Work> {
    return this.tryPutAsync<WorkUpdateForm, Work>(`/works`, form);
  }

  public searchWork(form: WorkSearchForm): Observable<Array<Work>> {
    return this.tryPost<WorkSearchForm, Array<Work>>(`/works/search`, form);
  }

  public getWorkOfUserById(id: number): Observable<Array<Work>> {
    return this.tryGet<Array<Work>>(`/works/user/${id}`);
  }

}
