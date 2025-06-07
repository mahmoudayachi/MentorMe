import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { StorageService } from '../storage.service';
import { AdminService } from '../admin.service';
import { NgClass, NgIf } from '@angular/common';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf,NgClass],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  
  listofmentees:any=[]
  listofmentors:any=[]
  menteesbuttonclicked : boolean = false
  mentorsbuttonclicked : boolean = false
  accountstatus: any
  secondoption : any
  constructor(private router:Router,private adminservice:AdminService){

  }
  

   
  getAccountstatus(event : Event , id : number ){
     this.accountstatus = (event.target as HTMLSelectElement).value 
     console.log(this.accountstatus)
     this.adminservice.changestatus(id,this.accountstatus).subscribe((res)=>{
      console.log(res)
      if(res.id!=null){
        alert("account status changed successfully ")
      }
      else{
        console.log(res.err.message)
      }
     })

  }
 
  getMentees(){
    this.menteesbuttonclicked=true
    this.mentorsbuttonclicked=false
    this.adminservice.getallmentee().subscribe((res)=>{
      this.listofmentees=res;
      console.log(this.listofmentees)
    })
  }

  getMentors(){
    this.mentorsbuttonclicked=true
    this.menteesbuttonclicked=false
    this.adminservice.getallmentor().subscribe((res)=>{
      this.listofmentors=res
      console.log(this.listofmentors)
    })
  }
  logout(){
    StorageService.logout();
    this.router.navigateByUrl("/adminlogin")
    
   }
}
