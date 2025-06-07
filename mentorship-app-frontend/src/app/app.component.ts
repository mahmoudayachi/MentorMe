import { Component, ElementRef } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import {} from '@fortawesome/angular-fontawesome'
import { StorageService } from './storage.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
   constructor(private  router:Router){
    
    
   }
   isAdminloggedin(){
    return StorageService.isAdminLoggedIn()
   }
  
   ismentorlogedin(){

    return StorageService.isMentorLoggedIn();
   }
  
   ismenteelogedin(){
    return StorageService.isMenteeLoggedIn();
   }
  ngOnInit(){
  console.log("hi")
  const hamburgermenu = document.querySelector('.hamburger-menu')
  const dropdownmenu = document.querySelector('.dropdown-menu')
  const closeicon = document.querySelector('.close-icon')
      hamburgermenu?.addEventListener('click',()=>{
        hamburgermenu.classList.toggle('active');
        dropdownmenu?.classList.toggle('active');
        closeicon?.classList.toggle('active')
      })

}

redirect(){
  StorageService.logout()
  this.router.navigateByUrl("/login")
  
}
 
  

}
