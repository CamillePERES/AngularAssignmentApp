import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WorkStudentComponent } from './work-student/work-student.component';
import { WorkProfessorComponent } from './work-professor/work-professor.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WorkItemComponent } from './work-item/work-item.component';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { MyWorkStudentComponent } from './my-work-student/my-work-student.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'student',
    component: MyWorkStudentComponent,
  }
];

@NgModule({
  declarations: [
    WorkStudentComponent,
    WorkProfessorComponent,
    WorkItemComponent,
    MyWorkStudentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
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
