import { Component, OnInit } from '@angular/core';
import {SongService} from "../song.service";
import {CommentService} from "../../comment/comment.service";
import {Song} from "../song";
import {ActivatedRoute, Router} from "@angular/router";
import {CommentOur} from "../../comment/comment";
import {RateService} from "../../rate/rate.service";
import {Rate} from "../../rate/rate";
import {User} from "../../user/user";
import {UserService} from "../../user/user.service";


@Component({
  selector: 'app-song-page',
  templateUrl: './song-page.component.html',
  styleUrls: ['./song-page.component.css'],
  providers:[SongService, CommentService, RateService, UserService]
})
export class SongPageComponent implements OnInit {

  songId:number;
  song:Song;
  comments:CommentOur[];
  rates: Rate[];
  user:User;
  songAverageRate:number;
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private songService: SongService,
              private rateService: RateService,
              private commentService: CommentService,
              private userService: UserService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.songId=params['songId'];
    });

    if(this.songId) {
      this.songService.getSongById(this.songId).subscribe(
        song => {
          this.song = song;
        }, error => {
          console.log(error);
        }
      );
    }

    //todo method which ll getAuthorForSong(songID)

    this.getAllRatesForSong(this.songId);
    this.getAllCommentsForSong(this.songId);
    this.getSongAverageRate(this.songId);
  }

  getAllCommentsForSong(songId:number){
    this.commentService.getAllCommentsForSong(songId).subscribe(
      comments => {
        this.comments=comments;
      },
      err => {
        console.log(err);
      }
    );
  }

  getAllRatesForSong(songId:number){
    this.rateService.getAllRatesForSong(songId).subscribe(
      rates => {
        this.rates=rates;
      },
      err => {
        console.log(err);
      }
    );
  }

  getSongAverageRate(songId:number){
    this.songService.getSongAverageRate(songId).subscribe(
      songAverageRate=>{
        this.songAverageRate=songAverageRate;
      },err=>{
        console.log(err);
      }
    )
  }

  redirectToSongList(){
    this.router.navigate(['/song'])
  }

  redirectToAuthorPage(authorId: number){
    if(authorId>0) {
      this.router.navigate(['/author/authorPage', authorId]);
    }
  }

  redirectToUserPage(userId: number){
    if(userId>0) {
      this.userService.getUserById(userId).subscribe(
        user=>{
          this.user=user;
          // if(user.nickname=isLogged)
          this.router.navigate(['/user/userPage', this.user.nickname]);
        },
        err=>{
          console.log(err);
        }
      );

    }
  }

}
