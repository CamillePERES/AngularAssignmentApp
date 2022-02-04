import { CourseService } from './../../core/course/course.service';
import { Component, OnInit } from '@angular/core';
import { Course } from 'src/app/core/course/course.type';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  listCourses: Array<Course> = [];

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

}
