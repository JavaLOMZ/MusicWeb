import {Injectable} from '@angular/core';
import {User} from "./user";
import {Http, Response, Headers} from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import {Observable} from "rxjs/Observable";
import {AuthenticationService} from "../authentication.service";

@Injectable()
export class UserService {

  private apiUrl = 'http://localhost:8080/user';

  constructor(private http: Http,
              private authenticationService: AuthenticationService) {
  }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });

  getAllUsers(): Observable<User[]> {
    return this.http.get(this.apiUrl, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getUserById(userId: number): Observable<User> {
    return this.http.get(this.apiUrl + '/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateUser(user: User): Observable<User> {
    return this.http.post(this.apiUrl+'/create', user, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteUserById(userId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getUserByUsername(username: string): Observable<User> {
    return this.http.get(this.apiUrl + '/nick/' + username, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
}
