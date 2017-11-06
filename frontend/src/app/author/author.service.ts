import { Injectable } from '@angular/core';
import {Headers, Http, Response} from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Observable } from "rxjs/Observable";
import {Author} from "./author";
import {AuthenticationService} from "../authentication.service";

@Injectable()
export class AuthorService {

  private apiUrl = 'http://localhost:8080/author';


  constructor(private http: Http,
              private authenticationService: AuthenticationService) {
  }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });


  getAllAuthors(): Observable<Author[]>  {
    return this.http.get(this.apiUrl, {headers: this.headers})
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAuthorById(authorId: number): Observable<Author> {
    return this.http.get(this.apiUrl+'/'+authorId, {headers: this.headers})
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateAuthor(author: Author): Observable<Author> {
    return this.http.post(this.apiUrl ,author, {headers: this.headers})
      .catch((error:any)=> Observable.throw(error.json().error || 'Server error'));
  }

  deleteAuthorById(authorId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + authorId, {headers: this.headers})
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }
}
