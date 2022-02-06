import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router"
import { forkJoin, Observable } from "rxjs";
import { AssignmentService } from "src/app/core/assignments/assignment.service";
import { CourseService } from "src/app/core/course/course.service";
import { Course } from "src/app/core/course/course.type";

@Injectable({
  providedIn: 'root'
})
export class AssignmentDetailsResolver implements Resolve<any>
{
  /**
   * Constructor
   */
  constructor(
      private service: AssignmentService
  )
  {
  }

  /**
   * Use this resolver to resolve initial
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any>
  {
    let id = route.params['id'];
    return this.service.getAssignmentById(id);
  }
}
