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


  getAllAuthors(): Observable<Author[]> {
    return this.http.get(this.apiUrl, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAuthorById(authorId: number): Observable<Author> {
    return this.http.get(this.apiUrl + '/' + authorId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateAuthor(author: Author): Observable<Author> {
    return this.http.post(this.apiUrl, author, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteAuthorById(authorId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + authorId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getUniqueAuthorByName(name: string): Observable<any> {
    return this.http.get(this.apiUrl + "/name/" + name, {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError) as Observable<any>;
  };

  getAuthorsBySearchWord(searchWord: string): Observable<any>{
    return this, this.http.get(this.apiUrl + '/search/' + searchWord,{headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllAuthorsSortedByName(): Observable<Author[]> {
    return this.http.get(this.apiUrl+'/sortedByName', {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllAuthorsSortedByYearOfBirth(): Observable<Author[]> {
    return this.http.get(this.apiUrl+'/sortedByYearOfBirth', {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllAuthorsSortedByCountryOfOrigin(): Observable<Author[]> {
    return this.http.get(this.apiUrl+'/sortedByCountryOfOrigin', {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

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
  export function authorNameTaken(authorService: AuthorService) {
    return control => new Promise((resolve, reject) => {
      console.log("in validator");
      authorService.getUniqueAuthorByName(control.value).subscribe(data => {
        console.log(data);
        if (data.authorId) {
          resolve({authorNameTaken: true})
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
