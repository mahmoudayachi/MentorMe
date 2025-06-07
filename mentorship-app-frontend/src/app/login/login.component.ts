import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { StorageService } from '../storage.service';
import { AuthService } from '../auth.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginform!:FormGroup
  selectedValue!: string;
  element:any
  erromessage:any

  onSelect(event:Event){
   this.selectedValue = (event.target as HTMLSelectElement).value;
  }

  constructor(private form:FormBuilder , private router:Router ,private authservice:AuthService , private route :ActivatedRoute){
    this.loginform =this.form.group({
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required]],
      
    })
  }
   
 

  onsubmit(){
    if(!this.loginform.valid || this.selectedValue == null){
       alert("all filed  must not be empty ")
     }
    else{
     this.authservice.loginMentee(this.loginform.value,this.selectedValue).subscribe((res)=>{
      console.log(this.selectedValue);
      
      if(res.userId!=null){
        alert('loged in successfully')
        const   user= {
          id:res.userId,
          role:res.userRole
        }
        StorageService.saveUser(user)
        StorageService.saveToken(res.jwt)
       
        
        if(StorageService.isMenteeLoggedIn()){
            this.router.navigateByUrl("/menteedashboard")
         }
         else if(StorageService.isMentorLoggedIn()){
            this.router.navigateByUrl("/mentordashboard")
      }
   
    
    }
    else{
      this.erromessage=res.message
      console.log(this.erromessage)
    }
     })
     
  }
}
}
