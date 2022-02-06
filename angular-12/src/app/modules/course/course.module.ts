import { MatCardModule } from '@angular/material/card';
import { CourseComponent } from './course.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { DetailsComponent } from './details/details.component';
import { NgbDatepickerModule, NgbModalModule, NgbModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { CourseDetailsResolver } from './resolver/course.details.resolver';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import {MatBadgeModule} from '@angular/material/badge';
import { MatIconModule } from '@angular/material/icon';

const routes: Routes = [
  {
    path: '',
    component: CourseComponent,
  },
  {
    path: 'details/:id',
    resolve : { initialData: CourseDetailsResolver },
    component: DetailsComponent,
  }
];

@NgModule({
  declarations: [
    CourseComponent,
    DetailsComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    RouterModule,
    MatCardModule,
    NgbPaginationModule,
    NgbModalModule,
    FormsModule,
    MatFormFieldModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    NgbDatepickerModule,
    ReactiveFormsModule,
    MatIconModule,
  ]
})
export class CourseModule { }
