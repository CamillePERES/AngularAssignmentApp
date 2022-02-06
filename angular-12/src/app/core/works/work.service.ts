import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BaseApiService } from "../base/baseapi.service";
import { IdentityService } from "../identity/identity.service";
import { Work, WorkCreateForm } from "./work.type";

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

  public async getWorkOfAssignmentById(id: number): Promise<Work | null>{
    return this.tryGetAsync<Work | null>(`/works/assignment/${id}`);
  }

  public async createWork(form: WorkCreateForm): Promise<Work> {
    return this.tryPostAsync<WorkCreateForm, Work>(`/works`, form);
  }

}
