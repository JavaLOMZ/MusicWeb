import {Component, OnInit} from '@angular/core';
import {SongService} from "../song.service";
import {CommentService} from "../../comment/comment.service";
import {Song} from "../song";
import {ActivatedRoute, Router} from "@angular/router";
import {CommentOur} from "../../comment/comment";
import {RateService} from "../../rate/rate.service";
import {Rate} from "../../rate/rate";
import {User} from "../../user/user";
import {UserService} from "../../user/user.service";
import {DomSanitizer} from "@angular/platform-browser";
import {AuthenticationService} from "../../authentication.service";
import {AuthorService} from "../../author/author.service";
import {Author} from "../../author/author";



@Component({
  selector: 'app-song-page',
  templateUrl: './song-page.component.html',
  styleUrls: ['./song-page.component.css'],
  providers: [SongService, CommentService, RateService, UserService, AuthenticationService,AuthorService]
})
export class SongPageComponent implements OnInit {

  songId: number;
  song: Song;
  comments: CommentOur[];
  user: User;
  songAverageRate: number;
  userId: number;
  rateOfUser: Rate;
  commentOfUser:CommentOur;
  authors: Author[];
  authorName:string;
  users: User[];
  userName: string;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private songService: SongService,
              private rateService: RateService,
              private commentService: CommentService,
              private userService: UserService,
              private authenticationService: AuthenticationService,
              private authorService: AuthorService,
              public sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.getSongIdFromParam();
    this.getSong();
    this.getAllCommentsForSong(this.songId);
    this.getSongAverageRate(this.songId);
    this.getRateForUserAndSong(this.songId);
    this.getCommentForUserAndSong(this.songId);
    this.getAllAuthors();
    this.getAllUsers();
  }


  getSongIdFromParam() {
    this.sub = this.route.params.subscribe(params => {
      this.songId = params['songId'];
    });
  }

  getSong() {
    if (this.songId) {
      this.songService.getSongById(this.songId).subscribe(
        song => {
          this.song = song;
        }, error => {
          console.log(error);
        }
      );
    }
  }

  getAllCommentsForSong(songId: number) {
    this.commentService.getAllCommentsForSong(songId).subscribe(
      comments => {
        this.comments = comments;
      },
      err => {
        console.log(err);
      }
    );
  }


  getSongAverageRate(songId: number) {
    this.songService.getSongAverageRate(songId).subscribe(
      songAverageRate => {
        this.songAverageRate = songAverageRate;
      }, err => {
        console.log(err);
      }
    )
  }

  redirectToSongList() {
    this.router.navigate(['/song'])
  }

  redirectToAuthorPage(authorId: number) {
    if (authorId > 0) {
      this.router.navigate(['/author/authorPage', authorId]);
    }
  }


  redirectToUserPage(userId: number) {
    if (userId > 0) {
      this.userService.getUserById(userId).subscribe(
        user => {
          this.user = user;
          // if(user.nickname=isLogged)
          this.router.navigate(['/user/userPage', this.user.nickname]);
        },
        err => {
          console.log(err);
        }
      );
    }
  }

  redirectToCreateComment(songId: number) {
    if (songId) {
      this.router.navigate(['/comment/createComment', songId])
    }
  }

  redirectToCreateRate(songId: number) {
    if (songId) {
      this.router.navigate(['/rate/createRate', songId])
    }
  }

  getRateForUserAndSong(songId:number) {
    this.userService.getUserByUsername(this.authenticationService.getUsername()).subscribe(
      user => {
        this.userId = user.userId;
        this.rateService.getRateForUserAndSong(this.userId, songId).subscribe(
          rate => {
            this.rateOfUser = rate;
          },err=>{
            console.log(err);
          }
        )
      }, err => {
        console.log(err);
      }
    )
  }

  getCommentForUserAndSong(songId:number){
    this.userService.getUserByUsername(this.authenticationService.getUsername()).subscribe(
      user=>{
        this.userId=user.userId;
        this.commentService.getCommentForUserAndSong(this.userId,songId).subscribe(
          comment=>{
            this.commentOfUser=comment;
          },err=>{
            console.log(err);
          }
        )
      },err=>{
        console.log(err);
      }
    )
  }

  getAllAuthors(){
    this.authorService.getAllAuthors().subscribe(
      authors=>{
        this.authors=authors
      }, err=>{
        console.log(err);
      }
    )
  }

  getAuthorName(authorId:number){
    this.authorName=this.authors.find(x=>x.authorId===authorId).name;
    return this.authorName;
  }

  getAllUsers(){
    this.userService.getAllUsers().subscribe(
      users=>{
        this.users=users
      }, err=>{
        console.log(err);
      }
    )
  }

  getUserName(userId:number){
    this.userName=this.users.find(x=>x.userId===userId).nickname;
    return this.userName;
  }
}
