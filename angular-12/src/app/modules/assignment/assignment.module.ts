import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentComponent } from './assignment.component';
import { RouterModule, Routes } from '@angular/router';
import { DetailsComponent } from './details/details.component';
import { AssignmentDetailsResolver } from './resolver/assignment.resolver';
import { WorkModule } from '../work/work.module';
import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [
  {
    path: 'details/:id',
    resolve : { initialData: AssignmentDetailsResolver },
    component: DetailsComponent,
  }
];

@NgModule({
  declarations: [
    AssignmentComponent,
    DetailsComponent
  ],
  imports: [
    CommonModule,
    WorkModule,
    NgbDatepickerModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AssignmentModule { }
