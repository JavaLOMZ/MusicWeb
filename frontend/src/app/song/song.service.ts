import {Headers, Http, Response} from "@angular/http";
import {User} from "../user/user";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Song} from "./song";
import {AuthenticationService} from "../authentication.service";
import {MusicGenre} from "./music.genre";
import {reject} from "q";

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
      .map(this.extractData)
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

  getSongsByAuthorId(authorId: number): Observable<Song[]> {
    return this.http.get(this.apiUrl + '/author/' + authorId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongAverageRate(songId: number): Observable<number> {
    return this.http.get(this.apiUrl + '/songAverageRate/' + songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getMusicGenreTypes(): Observable<MusicGenre[]> {
    return this.http.get(this.apiUrl + '/musicGenre', {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getRandomSongsByUserPreferences(userId: number): Observable<Song[]> {
    return this.http.get(this.apiUrl + '/user/recommendedSongs/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongByNameAndAuthor(songName: string, authorId: number): Observable<any> {
    return this, this.http.get(this.apiUrl + '/songName/' + songName + '/' + authorId,{headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongByName(songName: string): Observable<any> {
    return this, this.http.get(this.apiUrl + '/songName/' + songName,{headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongsBySearchWord(searchWord: string): Observable<any>{
    return this, this.http.get(this.apiUrl + '/search/' + searchWord,{headers: this.headers})
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
  export function songNameTaken(songService: SongService,authorId:number){
    return control => new Promise((resolve, reject) => {
      console.log("validator");
      songService.getSongByNameAndAuthor(control.value,authorId).subscribe(data=> {
        console.log(data);
        if (data.songId) {
          console.log(data.songName);
          console.log(data.authorId);
          resolve({songNameTaken: true})
        } else {
          resolve(null);
        }
      },(err)=>{
        console.log("in error"+err);
        if(err!="404- Not found"){
          resolve(null);
        }else {
          resolve(null);
        }
      });
    });
  }


