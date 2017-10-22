import { Injectable } from '@angular/core';
import { User } from "./user";
import { Http, Response } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Observable } from "rxjs/Observable";

@Injectable()
export class UserService {

  private apiUrl = 'http://localhost:8080/user';

  constructor(private http: Http) {
  }

  getAllUsers(): Observable<User[]>  {
    return this.http.get(this.apiUrl)
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  getUserById(userId: number): Observable<User> {
    return this.http.get(this.apiUrl+'/'+userId)
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateUser(user: User): Observable<User> {
    return this.http.post(this.apiUrl ,user)
      .catch((error:any)=> Observable.throw(error.json().error || 'Server error'));
  }

  deleteUserById(userId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + userId)
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }
}
