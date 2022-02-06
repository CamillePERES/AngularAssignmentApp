import { AssignmentService } from './../../../core/assignments/assignment.service';
import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router"
import { forkJoin, Observable, pipe } from "rxjs";
import { map } from "rxjs/operators";
import { CourseService } from "src/app/core/course/course.service";
import { Course } from "src/app/core/course/course.type";

@Injectable({
  providedIn: 'root'
})
export class CourseDetailsResolver implements Resolve<any>
{
  /**
   * Constructor
   */
  constructor(
      private service: CourseService,
      private assignmentService: AssignmentService
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
    return forkJoin([
      this.service.getCourseById(id),
      this.assignmentService.getAssignmentsOfCourse(id)
    ]).pipe(
      map(resp => {
        return { course: resp[0], assignments: resp[1] }
      }
    ));

  }
}
