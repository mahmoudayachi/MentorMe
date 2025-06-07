import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class SearchserviceService {
   
  constructor( private http:HttpClient) { }

    
  getmentorshiprequest(id : any){
    return this.http.get(`http://localhost:8080/mentorship-requests/${id}`)
  }
    getMentorById(id: string) {
    return this.http.get(`http://localhost:8080/search/${id}`);
  }

      sendrequest(request: any) {
    return this.http.post(`http://localhost:8080/mentorship-requests`,request);

     }

  searchmentor(filters:any){
    let params  = new HttpParams();

      params = params.set('jobtitle', filters.jobtitle);

 
      params = params.set('company', filters.company);
  
    
      params = params.set('category', filters.category);
    

      params = params.set('skills', filters.skills);
    
  
    return this.http.get('http://localhost:8080/search/search', { params });
  
}
}