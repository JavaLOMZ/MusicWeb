import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import {CommentService} from "../../comment/comment.service";
import {CommentOur} from "../../comment/comment";
import {RateService} from "../../rate/rate.service";
import {Rate} from "../../rate/rate";
import {AuthenticationService} from "../../authentication.service";
import {SongService} from "../../song/song.service";
import {Song} from "../../song/song";
@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
  providers:[UserService, CommentService, RateService, SongService]
})
export class UserPageComponent implements OnInit {

  username: string;
  userId:number;
  user: User;

  rates:Rate[];
  comments:CommentOur[];
  songs:Song[];
  songName: string;

  private sub:any;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private rateService: RateService,
              private songService: SongService,
              private commentService: CommentService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.username=params['username'],
      this.userId=params['userId'];
    });

    if(this.username) {
      this.userService.getUserByUsername(this.username).subscribe(
        user => {
          this.user = user;
        }, error => {
          console.log(error);
        }
      );
    }

    this.getAllRatesFromUserNickname(this.username);
    this.getAllCommentsFromUserNickname(this.username);
    this.getAllSongs();
  }


  getAllCommentsFromUserNickname(nickname: string){
    this.commentService.getAllCommentsFromUserByNickname(nickname).subscribe(
      comments => {
        this.comments=comments;
      },
      err => {
        console.log(err);
      }
    );
  }

  getAllRatesFromUserNickname(nickname: string){
    this.rateService.getAllRatesFromUserByNickname(nickname).subscribe(
      rates => {
        this.rates = rates;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectToSongPage(songId:number){
    if(songId>0){
      this.router.navigate(['/song/songPage',songId]);
    }
  }



  redirectToUserList(){
    this.router.navigate(['/user'])
  }

  getAllSongs() {
    this.songService.getAllSongs().subscribe(
      songs => {
        this.songs = songs
      },
      err => {
        console.log(err)
      }
    );
  }

  getSongName(songId:number){
    this.songName=this.songs.find(x=>x.songId===songId).songName;
    return this.songName;
  }
}
