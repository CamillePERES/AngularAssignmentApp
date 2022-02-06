import { CourseService } from 'src/app/core/course/course.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { Course } from 'src/app/core/course/course.type';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-course-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  public course: Course | null = null;
  public assignments: Array<Assignment> = [];
  public editMode: boolean = false;
  editForm: FormGroup | null = null;


  constructor(private route: ActivatedRoute, private formBuilder: FormBuilder, private courseService: CourseService, protected toast: ToastrService) {

   }

  ngOnInit(): void {
    this.course = this.route.snapshot.data.initialData.course;
    this.assignments = this.route.snapshot.data.initialData.assignments;
    console.log(this.assignments)
  }

  public goToEdit(){
    this.editMode = true;

    this.editForm = this.formBuilder.group({
      name: [this.course?.name, Validators.required],
      description: [this.course?.description, Validators.required],
    });
  }

  public async saveUpdate(){
    this.editMode = false;

    try{
      await this.courseService.updateCourse({name: this.editForm!.value.name, description: this.editForm!.value.description});
    }catch(error){
      this.toast.error("Update failed");
    }
  }

}
