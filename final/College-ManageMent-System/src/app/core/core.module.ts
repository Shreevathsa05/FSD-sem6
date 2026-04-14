import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/layout/header/header.component';
import { FooterComponent } from './components/layout/footer/footer.component';
import { LandingComponent } from './components/layout/landing/landing.component';
import { DepartmentComponent } from './components/views/department/department.component';
import { CourseComponent } from './components/views/course/course.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    LandingComponent,
    DepartmentComponent,
    CourseComponent
  ],
  imports: [
    CommonModule,ReactiveFormsModule
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    LandingComponent
  ]
})
export class CoreModule { }
