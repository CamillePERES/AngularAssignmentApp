import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentComponent } from './assignment.component';
import { RouterModule, Routes } from '@angular/router';
import { DetailsComponent } from './details/details.component';
import { AssignmentDetailsResolver } from './resolver/assignment.resolver';
import { WorkModule } from '../work/work.module';

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
    RouterModule.forChild(routes),
  ]
})
export class AssignmentModule { }
