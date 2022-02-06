import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkStudentComponent } from './work-student/work-student.component';
import { WorkProfessorComponent } from './work-professor/work-professor.component';



@NgModule({
  declarations: [
    WorkStudentComponent,
    WorkProfessorComponent
  ],
  imports: [
    CommonModule
  ]
})
export class WorkModule { }
