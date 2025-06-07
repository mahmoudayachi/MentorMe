import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const BASE_URL ="http://localhost:8080/";
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }
  signupMentee(signupRequest:any):Observable<any>{
    return this.http.post(BASE_URL+"api/auth/signup",signupRequest)
  }
  signupMentor(signupRequest:any):Observable<any>{
    return this.http.post(BASE_URL+"api/auth/signup/mentor",signupRequest)
  }
  loginMentee(loginRequest:any,accounttype:string):Observable<any>{
    if(accounttype=="MENTEE")
    return this.http.post(BASE_URL+"api/auth/Mentee/login",loginRequest)
   else {
    return this.http.post(BASE_URL+"api/auth/Mentor/login",loginRequest)
   }
  }

  loginAdmin(loginRequest:any):Observable<any>{
    return this.http.post(BASE_URL+"api/auth/Admin/login",loginRequest)
  }

}
