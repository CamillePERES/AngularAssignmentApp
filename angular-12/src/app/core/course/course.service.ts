import { Course, CourseForm, CourseSearchForm } from './course.type';
import { HttpClient } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";
import { Injectable } from '@angular/core';
import { environment } from "src/environments/environment";
import { Observable } from 'rxjs';
import { BaseApiService } from '../base/baseapi.service';
import { PaginationResult } from '../core.types';

@Injectable({
  providedIn: "root",
})
export class CourseService extends BaseApiService {

  constructor(http: HttpClient, toast: ToastrService) {
    super(http, toast)
  }

  public async getCoursesAsync(): Promise<Array<Course>> {
    try {
      return await this.http.get<Array<Course>>(`${environment.apiBaseUrl}/courses`).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display courses","Course");
      //console.log(error)
    }

    return [];
  }

  public getCourseById(id: number): Observable<Course> {
    return this.tryGet<Course>(`/courses/${id}`);
  }

  public async getCourseByIdAsync(id: number): Promise<void> {
    try {
      await this.http.get<Course>(`${environment.apiBaseUrl}/courses/${id}`).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display the course","Course");
    }
  }

  public async createCourseAsync(form: CourseForm): Promise<void>{
    try{
      await this.http.post<Course>(`${environment.apiBaseUrl}/courses/`, form).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't create the course","Course");
    }
  }

  public async deleteCourseById(id: number): Promise<void>{
    try{
      await this.http.delete<Course>(`${environment.apiBaseUrl}/courses/` + id).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't delete the course","Course");
    }
  }

  public async searchCoursesAsync(form: CourseSearchForm): Promise<PaginationResult<Course>> {
    return this.tryPostAsync('/courses/search', form);
  }
}
