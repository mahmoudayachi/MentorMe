import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchserviceService } from '../searchservice.service';
import { StorageService } from '../storage.service';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css'
})
export class DialogComponent {
requestform !: FormGroup
tab : any = []
id:any;
data!:string
  constructor(private form:FormBuilder , private router:Router ,private route: ActivatedRoute,private searchservice:SearchserviceService ){
    this.requestform =this.form.group({
      message:['',[Validators.required]],
      mentorId:parseInt(this.tab.id),
      menteeId:parseInt(this.data)
      
    })
}


 ngOnInit(){
     this.id = this.route.snapshot.paramMap.get('id');
     console.log(this.id)
      this.data= StorageService.getUser()
    console.log(this.data)
    this.data= this.data.slice(6,8)
     
     this.requestform.get("mentorId")?.setValue(this.id)
     this.requestform.get("menteeId")?.setValue(this.data)
     this.searchservice.getMentorById(this.id).subscribe((res=>{
      this.tab.push(res)
      console.log(this.tab)
     }))
 
   console.log(this.requestform.value)
  }

 Onsubmit(){
 console.log(this.requestform.value)
 this.searchservice.sendrequest(this.requestform.value).subscribe((res)=>{
   if(res){
    alert("request sent successfully")
   }
   
 })
}
}