import { Component } from '@angular/core';
import { StorageService } from '../storage.service';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-menteedashboard',
  standalone: true,
  imports: [],
  templateUrl: './menteedashboard.component.html',
  styleUrl: './menteedashboard.component.css'
})
export class MenteedashboardComponent {
  constructor( private router : Router ){
    
  }

logoutmentee(){
  StorageService.logout()
  this.router.navigateByUrl("/login")
}
}
