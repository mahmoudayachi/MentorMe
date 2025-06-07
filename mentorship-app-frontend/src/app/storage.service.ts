import { Injectable } from '@angular/core';

const Token ="token";
const USER ="user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token:string):void{
    window.localStorage.removeItem(Token);
    window.localStorage.setItem(Token,token)
  }

  static saveUser(user:any):void{
    window.localStorage.removeItem(user);
    window.localStorage.setItem(USER,JSON.stringify(user));
  }

  static getToken(): any {
    return localStorage.getItem(Token);
  }
  
  static getUser(): any {
    return localStorage.getItem(USER);
  }


  static getUserRole():any{
   const role   = localStorage.getItem(USER)
   if(role?.includes("MENTOR")){
 
     return role.slice(17,role.length-2)
   }
   else if(role?.includes("MENTEE")){
    return role?.slice(17,role.length-2)
   }
   else{
    return role?.slice(16,role.length-2)
   }
  }

  static isAdminLoggedIn():boolean{
    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role =="ADMIN";
  }

  static isMenteeLoggedIn():boolean{

    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role =='MENTEE';
  }


  static isMentorLoggedIn():boolean{

    if(this.getToken()=== null)
      return false

     const role:string = this.getUserRole();
     return role == 'MENTOR'
  }

  static getUserId():string {
    const user = this.getUser();
    if(user == null)
      return "";
    return user.id;
  }
  
  static logout() :void{
     window.localStorage.removeItem(Token);
     window.localStorage.removeItem(USER);
  }
}
