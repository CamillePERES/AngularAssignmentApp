import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkStudentComponent } from './work-student/work-student.component';
import { WorkProfessorComponent } from './work-professor/work-professor.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WorkItemComponent } from './work-item/work-item.component';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    WorkStudentComponent,
    WorkProfessorComponent,
    WorkItemComponent
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
