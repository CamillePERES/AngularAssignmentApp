import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BaseApiService } from "../base/baseapi.service";
import { IdentityService } from "../identity/identity.service";
import { Work } from "./work.type";

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

}
