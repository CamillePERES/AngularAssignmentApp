import { CourseService } from './../../core/course/course.service';
import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/core/course/course.type';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  listCourses: Array<Course> = [];

  // Pagination
  private _page: number = 1;
  get page() { return this._page; }
  set page(value: number) { this._page = value; }

  constructor(private courseService: CourseService) {

  }

  ngOnInit(): void {
    this.getCourses();
  }

  private async getCourses(){
    try {
      this.listCourses =  await this.courseService.getCoursesAsync();
      console.log("work");
    } catch (error) {
        console.log("error");
    }
  }

  formatInput(input: HTMLInputElement) {
    input.value = input.value.replace(FILTER_PAG_REGEX, '');
  }

  selectPage(page: string) {
    this.page = parseInt(page, 10) || 1;
  }

  public details(value: Course): void {
    console.log(value)
  }

}
