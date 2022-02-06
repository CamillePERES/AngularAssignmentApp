import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkStudentComponent } from './work-student/work-student.component';
import { WorkProfessorComponent } from './work-professor/work-professor.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WorkItemComponent } from './work-item/work-item.component';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { MyWorkStudentComponent } from './my-work-student/my-work-student.component';
import { MyWorkStudentItemComponent } from './my-work-student/my-work-student-item/my-work-student-item.component';



@NgModule({
  declarations: [
    WorkStudentComponent,
    WorkProfessorComponent,
    WorkItemComponent,
    MyWorkStudentComponent,
    MyWorkStudentItemComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbPaginationModule,
  ],
  exports: [
    WorkStudentComponent,
    WorkProfessorComponent,
    WorkItemComponent
  ]
})
export class WorkModule { }
