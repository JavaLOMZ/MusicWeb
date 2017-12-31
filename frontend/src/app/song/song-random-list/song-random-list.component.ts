import { Component, OnInit } from '@angular/core';
import {Song} from "../song";
import {SongService} from "../song.service";
import {Router} from "@angular/router";
import {UserService} from "../../user/user.service";
import {User} from "../../user/user";
import {AuthenticationService} from "../../authentication.service";
import {AuthorService} from "../../author/author.service";
import {Author} from "../../author/author";

@Component({
  selector: 'app-song-random-list',
  templateUrl: './song-random-list.component.html',
  styleUrls: ['./song-random-list.component.css'],
  providers:[SongService, UserService, AuthenticationService, AuthorService]
})
export class SongRandomListComponent implements OnInit {

  songs:Song[];
  user: User;
  userId: number;
  authors: Author[];
  authorName:string;

  constructor(private router:Router,
              private songService:SongService,
              private userService: UserService,
              private authenticationService: AuthenticationService,
              private authorService: AuthorService) { }

  ngOnInit() {
    this.getRandomSongsByUserPreferences();
    this.getAllAuthors();
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

  redirectToAuthorPage(authorId: number) {
    if (authorId > 0) {
      this.router.navigate(['/author/authorPage', authorId]);
    }
  }

}
