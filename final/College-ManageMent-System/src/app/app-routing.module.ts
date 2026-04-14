import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingComponent } from './core/components/layout/landing/landing.component';
import { CourseComponent } from './core/components/views/course/course.component';
import { DepartmentComponent } from './core/components/views/department/department.component';

const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'department', component: DepartmentComponent },
  { path: 'course', component: CourseComponent },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
