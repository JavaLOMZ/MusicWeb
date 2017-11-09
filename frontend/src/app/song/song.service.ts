import {Headers, Http, Response} from "@angular/http";
import {User} from "../user/user";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Song} from "./song";
import {AuthenticationService} from "../authentication.service";

@Injectable()
export class SongService {

  private apiUrl = 'http://localhost:8080/song';

  constructor(private http: Http,
              private authenticationService: AuthenticationService) {
  }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });

  getAllSongs(): Observable<Song[]> {
    return this.http.get(this.apiUrl, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongById(songId: number): Observable<Song> {
    return this.http.get(this.apiUrl + '/' + songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateSong(song: Song): Observable<Song> {
    return this.http.post(this.apiUrl, song, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteSongById(songId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongsByAuthorId(authorId:number): Observable<Song[]> {
    return this.http.get(this.apiUrl+'/author/'+authorId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongAverageRate(songId:number):Observable<number>{
    return this.http.get(this.apiUrl+'/songAverageRate/'+songId,{headers:this.headers})
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server error'));
  }
}
