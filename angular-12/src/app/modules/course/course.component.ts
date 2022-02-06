import { CourseService } from './../../core/course/course.service';
import { Component, OnInit } from '@angular/core';
import { Course, CourseSearchForm } from 'src/app/core/course/course.type';
import { ActivatedRoute, Router } from '@angular/router';
import { PaginationResult } from 'src/app/core/core.types';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  searchInputCourse: FormControl = new FormControl();
  searchInputUser: FormControl = new FormControl();


  // Pagination
  private paginationform: CourseSearchForm = { page: 1, pageSize: 5, courseName: '', userName: ''}
  public paginationresult: PaginationResult<Course> = {
    page: this.paginationform.page,
    pageSize: this.paginationform.pageSize,
    total: 0,
    totalPage: 0,
    results: []
  }
  get page() { return this.paginationform.page; }
  set page(value: number) { this.paginationform.page = value; this.getCourses(); }
  get pageSize() { return this.paginationform.pageSize; }
  set pageSize(value: number) { this.paginationform.pageSize = value; }

  constructor(
    private courseService: CourseService,
    private routeur: Router
    ) {

  }

  ngOnInit(): void {

    this.searchInputCourse.setValue(this.paginationform.courseName);
    this.searchInputCourse.valueChanges
    .pipe(takeUntil(this._unsubscribeAll),debounceTime(500),distinctUntilChanged())
    .subscribe(async (value: string | null) => {
      if(value != null) {
        this.paginationform.courseName = value;
        this.page = 1;
      }
    });

    this.searchInputUser.setValue(this.paginationform.userName);
    this.searchInputUser.valueChanges
    .pipe(takeUntil(this._unsubscribeAll),debounceTime(500),distinctUntilChanged())
    .subscribe(async (value: string | null) => {
      if(value != null) {
        this.paginationform.userName = value;
        this.page = 1;
      }
    });

    this.getCourses();
  }

  public setPageSize(value: number): void {
    this.paginationform.pageSize =value;
    this.getCourses();
  }

  private async getCourses(): Promise<void>{
    console.log(this.paginationform)
    try {
      this.paginationresult =  await this.courseService.searchCoursesAsync(this.paginationform);
      console.log(this.paginationresult)
    } catch (error) {
        console.log("error");
    }
  }

  public formatInput(input: HTMLInputElement): void {
    input.value = input.value.replace(FILTER_PAG_REGEX, '');
  }

  public selectPage(page: string): void {
    this.page = parseInt(page, 10) || 1;
  }

  public details(value: Course): void {
    console.log(value)
    this.routeur.navigate([`/course/details/${value.idcourse}`])
  }
}
