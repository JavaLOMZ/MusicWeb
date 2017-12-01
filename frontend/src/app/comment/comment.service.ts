import {Headers, Http, Response} from "@angular/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {CommentOur} from "./comment";
import {AuthenticationService} from "../authentication.service";

@Injectable()
export class CommentService {

  private apiUrl = 'http://localhost:8080/comment';

  constructor(private http: Http,
              private authenticationService: AuthenticationService) { }

  private headers = new Headers({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + this.authenticationService.getToken()
  });

  getCommentById(commentId: number): Observable<CommentOur> {
    return this.http.get(this.apiUrl + '/' + commentId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
  //todo same as delete, edit function in user-page
  createOrUpdateComment(comment: CommentOur): Observable<CommentOur> {
    return this.http.post(this.apiUrl, comment, {headers: this.headers})
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
  //todo i think it ll be nice to delete comments only in user-page view
  deleteCommentById(commentId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + commentId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllCommentsFromUser(userId: number): Observable<CommentOur[]> {
    return this.http.get(this.apiUrl + '/user/' + userId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllCommentsFromUserByNickname(nickname:string):Observable<CommentOur[]>{
    return this.http.get(this.apiUrl+'/user/nickname/'+nickname,{headers:this.headers})
      .map((res:Response)=>res.json())
      .catch((error:any)=>Observable.throw(error.json().error || 'Server error'));
  }

  getAllCommentsForSong(songId: number): Observable<CommentOur[]> {
    return this.http.get(this.apiUrl + '/song/' + songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getCommentForUserAndSong(userId:number, songId:number):Observable<CommentOur>{
    return this.http.get(this.apiUrl + '/user/' +userId+'/'+ songId, {headers: this.headers})
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
