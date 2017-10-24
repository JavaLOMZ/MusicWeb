import {Http, Response} from "@angular/http";
import {User} from "../user/user";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {CommentOur} from "./comment";

@Injectable()
export class CommentService {

  private apiUrl = 'http://localhost:8080/comment';

  constructor(private http: Http) {
  }


  getCommentById(commentId: number): Observable<CommentOur> {
    return this.http.get(this.apiUrl + '/' + commentId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server Error'));
  }
  //todo same as delete, edit function in user-page
  createOrUpdateComment(comment: CommentOur): Observable<CommentOur> {
    return this.http.post(this.apiUrl, comment)
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
  //todo i think it ll be nice to delete comments only in user-page view
  deleteCommentById(commentId: number): Observable<boolean> {
    return this.http.delete(this.apiUrl + '/' + commentId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllCommentsFromUser(userId: number): Observable<CommentOur[]> {
    return this.http.get(this.apiUrl + '/user/' + userId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getAllCommentsForSong(songId: number): Observable<CommentOur[]> {
    return this.http.get(this.apiUrl + '/song/' + songId)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }
}
