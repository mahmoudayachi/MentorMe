import { Component, inject } from '@angular/core';
import { SearchserviceService } from '../searchservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import {MatDialog} from '@angular/material/dialog'

@Component({
  selector: 'app-viewprofile',
  standalone: true,
  imports: [],
  templateUrl: './viewprofile.component.html',
  styleUrl: './viewprofile.component.css',

})
export class ViewprofileComponent {
 
  mentorinformation:any =[]
  mentorId: any;
  constructor( private searchservice:SearchserviceService,private route: ActivatedRoute,private router :Router){
  }
  viewProfile(id: string) {
  this.router.navigate(['/dialog', id]);
}
  ngOnInit(){
     this.mentorId = this.route.snapshot.paramMap.get('id');
     console.log(this.mentorId)
     this.searchservice.getMentorById(this.mentorId).subscribe((res=>{
       this.mentorinformation.push(res)
       console.log(this.mentorinformation)
     }))
  }
}
