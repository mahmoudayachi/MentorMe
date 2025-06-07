import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SearchserviceService } from '../searchservice.service';
import { debounceTime } from 'rxjs';
import { Router } from '@angular/router';


@Component({
  selector: 'app-search',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchform !:FormGroup
  selectedvalue!:any
  valuejobtitle !: any
  listofmentor:any= []
 

 constructor(  private form:FormBuilder,private searchservice:SearchserviceService,private router :Router){
    this.searchform = form.group({
       skills:[null,[Validators.required,Validators.email]],
       jobtitle:[null,[Validators.required]],
       company:[null,[Validators.required]],
       category:[null,[Validators.required]]   
    })
   
 }

 ngOnInit(): void {
    this.searchform.get("category")?.setValue("Engineering")
      this.searchservice.searchmentor(this.searchform.value).subscribe((res)=>{
      console.log(this.searchform.value)
      this.listofmentor =res
      console.log(this.listofmentor)
      })
  }


  onCheckboxChange( event: any) {
   if(event.target.checked){
    this.searchform.get("category")?.setValue(null)
    console.log(this.listofmentor)
    console.log('checkbox is checked')
    console.log(event.target.value)
    this.searchform.get("skills")?.setValue(event.target.value)
    this.searchservice.searchmentor(this.searchform.value).subscribe((res)=>{
      console.log(this.searchform.value)
     
      this.listofmentor =res
      console.log(this.listofmentor)
   
     this.onsubmit();
  })

  }
}
 onCheckboxjobChange( event: any) {
   if(event.target.checked){
    this.searchform.get("category")?.setValue(null)
    console.log(this.listofmentor)
    console.log('checkbox is checked')
    console.log(event.target.value)
    this.searchform.get("jobtitle")?.setValue(event.target.value)
    this.searchservice.searchmentor(this.searchform.value).subscribe((res)=>{
      console.log(this.searchform.value)
     
      this.listofmentor =res
      console.log(this.listofmentor)
   
     this.onsubmit();
  })

  }
}

 onCheckboxcompanyChange( event: any) {
   if(event.target.checked){
    this.searchform.get("category")?.setValue(null)
    console.log(this.listofmentor)
    console.log('checkbox is checked')
    console.log(event.target.value)
    this.searchform.get("company")?.setValue(event.target.value)
    this.searchservice.searchmentor(this.searchform.value).subscribe((res)=>{
      console.log(this.searchform.value)
     
      this.listofmentor =res
      console.log(this.listofmentor)
   
     this.onsubmit();
  })

  }
}

 viewProfile(id: string) {
  this.router.navigate(['/mentors', id]);
}
 onsubmit(){
   this.searchservice.searchmentor(this.searchform.value).subscribe((res)=>{
      console.log(this.searchform.value)
      this.listofmentor =res
      console.log(this.listofmentor)
   })
 }

   
}
