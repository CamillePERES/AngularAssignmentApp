import { MatCardModule } from '@angular/material/card';
import { CourseComponent } from './course.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { DetailsComponent } from './details/details.component';
import { NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [
  {
  }
];

@NgModule({
  declarations: [
    CourseComponent,
    DetailsComponent,
  ],
  imports: [
    CommonModule,
    MatCardModule,
    NgbPaginationModule
  ]
})
export class CourseModule { }
