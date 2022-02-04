import { MatCardModule } from '@angular/material/card';
import { CourseComponent } from './course.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';

const routes: Routes = [
  {
  }
];

@NgModule({
  declarations: [
    CourseComponent,
  ],
  imports: [
    CommonModule,
    MatCardModule
  ]
})
export class CourseModule { }
