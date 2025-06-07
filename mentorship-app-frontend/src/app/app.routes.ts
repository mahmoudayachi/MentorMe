import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SingupComponent } from './singup/singup.component';
import { SearchComponent } from './search/search.component';
import { AdminDashboardComponent } from './admindashboard/admin-dashboard.component';
import { HomeComponent } from './home/home.component';
import { MenteedashboardComponent } from './menteedashboard/menteedashboard.component';
import { MentordashboardComponent } from './mentordashboard/mentordashboard.component';
import { AdminloginComponent } from './adminlogin/adminlogin.component';
import { MentorsignupComponent } from './mentorsignup/mentorsignup.component';
import { ViewprofileComponent } from './viewprofile/viewprofile.component';
import { DialogComponent } from './dialog/dialog.component';


export const routes: Routes = [
    {path:"login",component:LoginComponent},
    {path:"signup",component:SingupComponent},
    {path:"search",component:SearchComponent},
    {path:"adminlogin",component:AdminloginComponent},
    {path:"admindashboard",component:AdminDashboardComponent},
    {path:"home",component:HomeComponent },
    {path:"",redirectTo:'home',pathMatch:"full"},
    {path:"menteedashboard",component:MenteedashboardComponent},
    {path:"mentordashboard",component:MentordashboardComponent},
    {path:"mentorsignup",component:MentorsignupComponent},
    {path:"mentors/:id",component:ViewprofileComponent},
    {path:"dialog/:id",component:DialogComponent}
    
];
