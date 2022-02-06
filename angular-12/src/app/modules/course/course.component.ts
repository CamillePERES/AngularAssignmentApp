
import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { PaginationResult } from 'src/app/core/core.types';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { CourseService } from 'src/app/core/course/course.service';
import { Course, CourseSearchForm } from 'src/app/core/course/course.type';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IdentityService } from 'src/app/core/identity/identity.service';
import { User } from 'src/app/core/user/user.type';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  private user: User | null = null;
  get isTeacher() { return this.user != null && this.user.role === 'TEACHER'}

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  searchInputCourse: FormControl = new FormControl();
  searchInputUser: FormControl = new FormControl();

  // creation
  public createForm: FormGroup | null = null;
  private modal : NgbModalRef | null = null;

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
    private routeur: Router,
    private modalService: NgbModal,
    private formBuilder: FormBuilder,
    private identityService: IdentityService
    ) {

  }

  ngOnInit(): void {

    this.identityService.identity$
    .pipe(takeUntil(this._unsubscribeAll))
    .subscribe(user => this.user = user);

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

  public openVerticallyCentered(content: NgbModal): void {
    this.createForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
    this.modal = this.modalService.open(content, { centered: true, backdrop : 'static', keyboard : false});
  }

  public closeModal(): void {
    if(this.modal != null){
      this.modal.close();
      this.modal = null;
    }
  }

  public async createCourse(): Promise<void> {

    if(this.createForm == null || this.createForm.invalid)
      return;

    this.createForm.disable();

    const value = this.createForm.getRawValue();
    const result = await this.courseService.createCourseAsync(value)

    if(result == null){
      this.createForm.enable();
      return;
    }

    this.closeModal();
    this.details(result);
  }
}
