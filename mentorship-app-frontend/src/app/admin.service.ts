import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';


const BASE_URL="http://localhost:8080/admin"

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  constructor(private http:HttpClient  )   { }

 
  changestatus(id:number,accountstatus:string):Observable<any>{
    return this.http.put(`${BASE_URL}/update/${id}/${accountstatus}`,{
      headers:this.createAuthorizationHeader()
    })
     
  }


  getallmentee():Observable<any>{
    return this.http.get(BASE_URL+"/mentee/all",{
      headers:this.createAuthorizationHeader()
    })
  }

  getallmentor():Observable<any>{
    return this.http.get(BASE_URL+"/mentor/all",{
      headers:this.createAuthorizationHeader()
    })
  }

  
  private createAuthorizationHeader():HttpHeaders {
    return new HttpHeaders().set(
      'Authorization','Bearer '+StorageService.getToken()                  )
    
  }
}
