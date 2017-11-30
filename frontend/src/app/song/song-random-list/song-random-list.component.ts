import { Component, OnInit } from '@angular/core';
import {Song} from "../song";
import {SongService} from "../song.service";
import {Router} from "@angular/router";
import {UserService} from "../../user/user.service";
import {User} from "../../user/user";
import {AuthenticationService} from "../../authentication.service";

@Component({
  selector: 'app-song-random-list',
  templateUrl: './song-random-list.component.html',
  styleUrls: ['./song-random-list.component.css'],
  providers:[SongService, UserService, AuthenticationService]
})
export class SongRandomListComponent implements OnInit {

  songs:Song[];
  user: User;
  userId: number;
  constructor(private router:Router,
              private songService:SongService,
              private userService: UserService,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.getRandomSongsByUserPreferences();
  }


  getRandomSongsByUserPreferences(){
    this.userService.getUserByUsername(this.authenticationService.getUsername()).subscribe(
      user=>{
        this.userId=user.userId;
        this.songService.getRandomSongsByUserPreferences(this.userId).subscribe(
          songs=>{
            this.songs=songs;
          },err=>{
            console.log(err);
          }
        )
      },err=>{
        console.log(err);
      }
    )
  }

  redirectSingleSongPage(songId : number) {
    if(songId) {
      this.router.navigate(['/song/songPage', songId]);
    }
  }

}
