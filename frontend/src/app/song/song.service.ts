import {Http, Response} from "@angular/http";
import {User} from "../user/user";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {Song} from "./song";

@Injectable()
export class SongService {

  private apiUrl = 'http://localhost:8080/song';

  constructor(private http: Http) {
  }

  getAllSongs(): Observable<Song[]> {
    return this.http.get(this.apiUrl)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongById(songId: number): Observable<Song> {
    return this.http.get(this.apiUrl + '/' + songId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }

  createOrUpdateSong(song: Song): Observable<Song> {
    return this.http.post(this.apiUrl, song)
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  deleteSongById(songId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + songId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getSongsByAuthorId(authorId:number): Observable<Song[]> {
    return this.http.get(this.apiUrl+'/author/'+authorId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
