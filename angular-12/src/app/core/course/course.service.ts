import { Course, CourseForm, CourseSearchForm, CourseFormUpdate} from './course.type';
import { HttpClient, HttpEvent, HttpEventType, HttpRequest, HttpResponse } from "@angular/common/http";
import { ToastrService } from "ngx-toastr";
import { Injectable } from '@angular/core';
import { environment } from "src/environments/environment";
import { Observable } from 'rxjs';
import { BaseApiService } from '../base/baseapi.service';
import { Assignment } from '../assignments/assignment.type';
import { PaginationResult, ProgressAction } from '../core.types';

@Injectable({
  providedIn: "root",
})
export class CourseService extends BaseApiService {

  constructor(http: HttpClient, toast: ToastrService) {
    super(http, toast)
  }

  public async getCoursesAsync(): Promise<Array<Course>> {
    try {
      return await this.tryGet<Array<Course>>(`/courses`).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display courses","Course");
      //console.log(error)
    }

    return [];
  }

  public async getCourseByIdAsync(id: number): Promise<Course | null> {
    try {
      return await this.tryGet<Course>(`/courses/${id}`).toPromise();
    } catch (erroer: any) {
      this.toast.error("Couldn't display the course","Course");
    }
    return null;
  }

  public async createCourseAsync(form: CourseForm): Promise<Course | null>{
    try{
      return await this.tryPost<CourseForm, Course>(`/courses`, form).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't create the course","Course");
    }
    return null;
  }

  public async deleteCourseByIdAsync(id: number): Promise<void>{
    try{
      await this.http.delete<Course>(`${environment.apiBaseUrl}/courses/` + id).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't delete the course","Course");
    }
  }

  public async updateCourse(form: CourseFormUpdate): Promise<Course | null>{
    console.log(form);
    try{
      return await this.tryPut<CourseFormUpdate, Course>(`/courses`, form).toPromise();
    }catch(error:any){
      this.toast.error("Couldn't edit the course","Course");
    }
    return null;
  }

  public getCourseById(id: number): Observable<Course> {
    return this.tryGet<Course>(`/courses/${id}`);
  }


  public async searchCoursesAsync(form: CourseSearchForm): Promise<PaginationResult<Course>> {
    return this.tryPostAsync('/courses/search', form);
  }

  public uploadPicture(courseid: number, file: File, callback: ProgressAction): void {

    callback({ value: 0, filename: file.name });

    const observer = {
      next: (event: any) => {
        if (event.type === HttpEventType.UploadProgress) callback({value: Math.round(100 * event.loaded / event.total), filename: file.name });
        else if (event instanceof HttpResponse) callback({ value: 100, filename: file.name});
      },
      error: (err: any) => {
        callback({ value: 0, filename: file.name });
      }
    }

    this.upload(courseid, file).subscribe(observer);
  }

  private upload(courseid: number, file: File): Observable<HttpEvent<any>> {

    const formData: FormData = new FormData();

    formData.append('picture', file);

    const req = new HttpRequest('POST', `${environment.apiBaseUrl}/courses/savepic/${courseid}`, formData, {
        reportProgress: true,
        responseType: 'json'
    });

    return this.http.request(req);
  }
}
