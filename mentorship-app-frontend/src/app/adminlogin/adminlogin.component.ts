import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { StorageService } from '../storage.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-adminlogin',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf],
  templateUrl: './adminlogin.component.html',
  styleUrl: './adminlogin.component.css'
})
export class AdminloginComponent {
   adminloginform!:FormGroup
  
   constructor(private router: Router, private form :FormBuilder,private authservice : AuthService){
      this.adminloginform =this.form.group({
            email:[null,[Validators.required,Validators.email]],
            password:[null,[Validators.required]],
            
          })
   }
  

 Onsubmit(){
  if(!this.adminloginform.valid){
    alert("all fields must not be empty")
  }
  else{
  this.authservice.loginAdmin(this.adminloginform.value).subscribe((res)=>{
    console.log(res)
    if(res.userId!=null){
      alert("logged in successfully")


      const   user= {
        id:res.userId,
        role:res.userRole
      }
     
       StorageService.saveUser(user)
       StorageService.saveToken(res.jwt)
       if(StorageService.isAdminLoggedIn()){
        this.router.navigateByUrl("/admindashboard")
       }

    }

  })
 }
}
}
