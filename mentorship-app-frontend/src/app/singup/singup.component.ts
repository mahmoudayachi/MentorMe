import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { defaultEquals } from '@angular/core/primitives/signals';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-singup',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf],
  templateUrl: './singup.component.html',
  styleUrl: './singup.component.css'
})
export class SingupComponent {
 signupform !:FormGroup
 userrole : string = "MENTEE"
 msg!:string
 constructor( private router : Router ,private form:FormBuilder,private authservice : AuthService){
    this.signupform = form.group({
       email:[null,[Validators.required,Validators.email]],
       password:[null,[Validators.required]],
       firstname:[null,[Validators.required]],
       lastname:[null,[Validators.required]],
       userrole:this.userrole
            
    })
 }

 OnSubmit(){
    if(!this.signupform.valid){
       alert("all filed  must not be empty ")
     }
    else{
    console.log(this.signupform.value);
    this.authservice.signupMentee(this.signupform.value).subscribe((res)=>{
     if(res.firstname!=null && res.lastname!=null && res.email!=null && res.password!=null){
      this.msg="mentee account created successfully "
      alert(this.msg)
      alert("you can login to your account ")
      this.router.navigateByUrl("/login")
     }
  
  })
  }
 }
}
