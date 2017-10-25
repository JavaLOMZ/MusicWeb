import { Injectable } from '@angular/core';
import { Http, Response } from "@angular/http";
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Observable } from "rxjs/Observable";
import {Author} from "./author";

@Injectable()
export class AuthorService {

  private apiUrl = 'http://localhost:8080/author';

  constructor(private http: Http) {
  }

  getAllAuthors(): Observable<Author[]>  {
    return this.http.get(this.apiUrl)
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAuthorById(authorId: number): Observable<Author> {
    return this.http.get(this.apiUrl+'/'+authorId)
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateAuthor(author: Author): Observable<Author> {
    return this.http.post(this.apiUrl ,author)
      .catch((error:any)=> Observable.throw(error.json().error || 'Server error'));
  }

  deleteAuthorById(authorId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + authorId)
      .map((res:Response) => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }
}
