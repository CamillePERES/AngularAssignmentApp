import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router"
import { forkJoin, Observable } from "rxjs";
import { CourseService } from "src/app/core/course/course.service";
import { Course } from "src/app/core/course/course.type";

@Injectable({
  providedIn: 'root'
})
export class CourseDetailsResolver implements Resolve<Course>
{
  /**
   * Constructor
   */
  constructor(
      private service: CourseService
  )
  {
  }

  /**
   * Use this resolver to resolve initial
   *
   * @param route
   * @param state
   */
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Course>
  {
    let id = route.params['id'];
    return this.service.getCourseById(id);
  }
}
