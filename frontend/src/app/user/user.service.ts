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
    'Content-Type': 'application/json; charset=UTF-8',
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
    return this.http.post(this.apiUrl + '/create', user, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteUserById(userId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getUserByUsername(username: string): Observable<any> {
    return this.http.get(this.apiUrl + "/nick/" + username)
      .map(this.extractData)
      .catch(this.handleError) as Observable<any>;
  };

  getUserByEmail(email: string): Observable<any> {
    return this.http.get(this.apiUrl + "/email/" + email)
      .map(this.extractData)
      .catch(this.handleError) as Observable<any>;
  };

  handleError(error: any) {
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg);
    return Observable.throw(errMsg);
  }

  extractData<T>(res: Response) {
    let body = res.json();
    return <T>body;
  }
}

  export function usernameTaken(userService: UserService) {
    return control => new Promise((resolve, reject) => {
      console.log("in validator");
      userService.getUserByUsername(control.value).subscribe(data => {
        console.log(data);
        if (data.userId) {
          resolve({usernameTaken: true})
        } else {
          resolve(null);
        }
      }, (err) => {
        console.log("in error" + err);
        if (err != "404 - Not Found") {
          resolve(null);
        } else {
          resolve(null);
        }
      });
    });
  }

  export function emailTaken(userService: UserService) {
    return control => new Promise((resolve, reject) => {
    console.log("in validator");
    userService.getUserByEmail(control.value).subscribe(data => {
      console.log(data);
      console.log(control.value);
      if (data.userId) {
        resolve({emailTaken: true})
      } else {
        resolve(null);
      }
    }, (err) => {
      console.log("in error" + err);
      if (err != "404 - Not Found") {
        resolve(null);
      } else {
        resolve(null);
      }
    });
  });
}
