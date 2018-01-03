import { Injectable } from '@angular/core';
import {Headers, Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Rate} from "./rate";
import {AuthenticationService} from "../authentication.service";
import {CommentOur} from "../comment/comment";

@Injectable()
export class RateService {

  private apiUrl = 'http://localhost:8080/rate';


  constructor(private http: Http,
              private authenticationService: AuthenticationService) { }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });

  getRateById(rateId: number): Observable<Rate> {
    return this.http.get(this.apiUrl + '/' + rateId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateRate(rate: Rate): Observable<Rate> {
    return this.http.post(this.apiUrl, rate, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteRateById(rateId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + rateId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllRatesFromUser(userId: number): Observable<Rate[]> {
    return this.http.get(this.apiUrl + '/user/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllRatesForSong(songId: number): Observable<Rate[]> {
    return this.http.get(this.apiUrl + '/song/' + songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllRatesFromUserByNickname(nickname:string):Observable<Rate[]>{
    return this.http.get(this.apiUrl+'/user/nickname/'+nickname,{headers:this.headers})
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server error'));
  }

  getRateForUserAndSong(userId:number, songId:number): Observable<Rate>{
    return this.http.get(this.apiUrl + '/' + userId+'/'+songId, {headers: this.headers})
      .map((res:Response)=>res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
}
