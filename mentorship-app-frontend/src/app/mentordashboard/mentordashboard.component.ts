import { Component } from '@angular/core';
import { StorageService } from '../storage.service';
import { SearchserviceService } from '../searchservice.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-mentordashboard',
  standalone: true,
  imports: [NgIf],
  templateUrl: './mentordashboard.component.html',
  styleUrl: './mentordashboard.component.css'
})
export class MentordashboardComponent {
  mentorrequestclicked : boolean = false
  currentmentorid :any
  listofrequest: any = []
 constructor(private searchservice:SearchserviceService){
  
 }

ngOnInit(){
   this.mentorrequestclicked  = true
  this.currentmentorid= StorageService.getUser()
    console.log(this.currentmentorid)
  this.currentmentorid= this.currentmentorid.slice(6,8)
   console.log(this.currentmentorid)
   this.searchservice.getmentorshiprequest(parseInt(this.currentmentorid)).subscribe((res)=>{
    this.listofrequest.push(res)
     console.log(this.listofrequest)
   })

}


}