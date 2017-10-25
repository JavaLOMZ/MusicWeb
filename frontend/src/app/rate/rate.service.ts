import { Injectable } from '@angular/core';
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Rate} from "./rate";

@Injectable()
export class RateService {

  private apiUrl = 'http://localhost:8080/rate';


  constructor(private http: Http) { }

  getRateById(rateId: number): Observable<Rate> {
    return this.http.get(this.apiUrl + '/' + rateId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
  //todo same as delete, edit function in user-page
  createOrUpdateRate(rate: Rate): Observable<Rate> {
    return this.http.post(this.apiUrl, rate)
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
  //todo i think it ll be nice to delete rates only in user-page view
  deleteRateById(rateId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + rateId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllRatesFromUser(userId: number): Observable<Rate[]> {
    return this.http.get(this.apiUrl + '/user/' + userId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllRatesForSong(songId: number): Observable<Rate[]> {
    return this.http.get(this.apiUrl + '/song/' + songId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

}
