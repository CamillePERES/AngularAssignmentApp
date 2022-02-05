import { CourseComponent } from './modules/course/course.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmptyComponent } from './layouts/empty/empty.component';

import { FullComponent } from './layouts/full/full.component';
import { AssignmentComponent } from './modules/assignment/assignment.component';
import { NoAuthGuard } from './core/guards/noAuth.guard';
import { AuthGuard } from './core/guards/auth.guard';

export const Approutes: Routes = [
  {path: '', pathMatch : 'full', redirectTo: 'dashboard'},
  {path: 'signed-in-redirect', pathMatch : 'full', redirectTo: 'dashboard'},
  {
    path:'',
    canActivate: [NoAuthGuard],
    canActivateChild: [NoAuthGuard],
    component: EmptyComponent,
    children: [
      {
        path: 'signin',
        loadChildren: () => import('./modules/signin/signin.module').then(m => m.SigninModule)
      },
      {
        path: 'signup',
        loadChildren: () => import('./modules/signup/signup.module').then(m => m.SignupModule)
      }
    ]
  },
  {
    path: '',
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    component: FullComponent,
    children: [
      { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule)
      },
      {
        path: 'component',
        loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule)
      },
      {path:'', redirectTo: '/assignments', pathMatch: 'full'},
      {
        path: 'assignment',
        component: AssignmentComponent,
        loadChildren: () => import('./modules/assignment/assignment.module').then(m => m.AssignmentModule)
      },
      {path:'', redirectTo: '/courses', pathMatch: 'full'},
      {
        path: 'course',
        component: CourseComponent,
        loadChildren: () => import('./modules/course/course.module').then(m => m.CourseModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: '/dashboard'
  }
];
