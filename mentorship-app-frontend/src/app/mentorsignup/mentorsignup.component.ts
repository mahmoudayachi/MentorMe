import { Component, inject } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatStepperModule} from '@angular/material/stepper';
import {MatButtonModule} from '@angular/material/button';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { signalUpdateFn } from '@angular/core/primitives/signals';
import { filter, Observable } from 'rxjs';
import { IfStmt, NonNullAssert } from '@angular/compiler';
import { HttpClient, HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-mentorsignup',
  standalone: true,
  imports: [MatButtonModule,
    MatStepperModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    CommonModule,
    NgIf,
    HttpClientModule
  
  ],
  templateUrl: './mentorsignup.component.html',
  styleUrl: './mentorsignup.component.css'
})
export class MentorsignupComponent {
  private _formBuilder = inject(FormBuilder);
  private authservice = inject(AuthService)
  private router = inject(Router)
   userrole : string = "MENTOR"
   private selectedfile:any = null ;
   category: string ="";
   validlink :string="";
   countries  = ["Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", 
  "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", 
  "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", 
  "China", "Colombia", "Comoros", "Congo (Congo-Brazzaville)", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic (Czechia)", "Democratic Republic of the Congo", 
  "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia", 
  "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", 
  "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", 
  "Kazakhstan", "Kenya", "Kiribati", "Korea, North", "Korea, South", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", 
  "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", 
  "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", 
  "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", 
  "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", 
  "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", 
  "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", 
  "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", 
  "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"]; 

   selectedCountry: string="";


 
   constructor(private http: HttpClient) {}
   
 

   
  

  onCountryChange(event: any) {
    this.selectedCountry = event.target.value;
    this.firstFormGroup.get("location")?.setValue(this.selectedCountry)
  }

   onSelectcategory(event:Event){
    this.category = (event.target as HTMLSelectElement).value;
     this.firstFormGroup.get("category")?.setValue(this.category)
   }

   onSelect(event: any){
     this.selectedfile = <File> event.target.files[0]
     console.log(this.selectedfile)
   }


   validation(event:Event){
    this.validlink = (event.target as HTMLSelectElement).value;
    if(!this.validlink.includes(" http www.linkedin.com")){
       alert("enter a valid linkedin link")
    }
     this.firstFormGroup.get("linkedinlink")?.setValue(this.validlink)
    console.log(this.validlink)
   }

  firstFormGroup = this._formBuilder.group({
    firstname: ['',Validators.required],
    lastname:['',Validators.required],
    email:['',Validators.required],
    password:['',Validators.required],
    location:['',Validators.required],
    company:['',Validators.required],
    jobtitle:['',Validators.required],
    category: ['',Validators.required],
    skills: ['', Validators.required],
    bio: ['', Validators.required],
    linkedinlink: ['',Validators.required],
    userrole:this.userrole
    
  });
 
  
  
  onsubmit(){
    if(!this.firstFormGroup.valid ){
      alert("all filed  must not be empty ")
    }
    else{
      
     const data = new FormData()
     data.append("firstname",this.firstFormGroup.controls.firstname.value??'')
     data.append("lastname",this.firstFormGroup.controls.lastname.value??'')
     data.append("email",this.firstFormGroup.controls.email.value??'')
     data.append("password",this.firstFormGroup.controls.password.value??'')
     data.append("location",this.firstFormGroup.controls.location.value??'')
     data.append("company",this.firstFormGroup.controls.company.value??'')
     data.append("jobtitle",this.firstFormGroup.controls.jobtitle.value??'')
     data.append("category",this.firstFormGroup.controls.category.value??'')
     data.append("skills",this.firstFormGroup.controls.skills.value??'')
     data.append("bio",this.firstFormGroup.controls.bio.value??'')
     data.append("linkedinlink",this.firstFormGroup.controls.linkedinlink.value??'')
     data.append("userrole",this.firstFormGroup.controls.userrole.value??'')
     data.append("image",this.selectedfile,this.selectedfile.name)
     console.log(data.get("image"))
     console.log(this.firstFormGroup.value)
     console.log(data.getAll)

     this.authservice.signupMentor(data).subscribe((res)=>{
      if(res!=null){
        alert("mentor signuped successfully ")
        this.router.navigateByUrl("/login")
      }
      else{
        console.log(res.error)
        alert("error")
      }
     })
    }
   
  }
  
  isLinear = false;
}
