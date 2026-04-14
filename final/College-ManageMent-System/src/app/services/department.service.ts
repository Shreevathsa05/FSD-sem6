import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Department } from '../models/department';
import { Observable } from 'rxjs';
import { DepartmentRESPONSE } from '../models/response_dto/department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http:HttpClient) { }
  addDept(department:Department):Observable<any>{
    return this.http.post("/api/departments",department);
  }
  getDepts():Observable<DepartmentRESPONSE[]>{
    return this.http.get<DepartmentRESPONSE[]>("/api/departments");
  }
}
