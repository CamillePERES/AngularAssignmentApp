import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AssignmentComponent } from './assignment.component';
import { Routes } from '@angular/router';
import { DetailsComponent } from './details/details.component';
import { AssignmentDetailsResolver } from './resolver/assignment.resolver';

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
    CommonModule
  ]
})
export class AssignmentModule { }
