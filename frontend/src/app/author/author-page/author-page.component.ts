import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../author.service";
import {SongService} from "../../song/song.service";
import {User} from "../../user/user";
import {Author} from "../author";
import {Song} from "../../song/song";
import {CommentService} from "../../comment/comment.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../user/user.service";

@Component({
  selector: 'app-author-page',
  templateUrl: './author-page.component.html',
  styleUrls: ['./author-page.component.css'],
  providers:[AuthorService, SongService]
})
export class AuthorPageComponent implements OnInit {

  authorId: number;
  author: Author;

  songs:Song[];
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authorService: AuthorService,
              private songService: SongService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.authorId=params['authorId'];
    });


    if(this.authorId) {
      this.authorService.getAuthorById(this.authorId).subscribe(
        author => {
          this.author = author;
        }, error => {
          console.log(error);
        }
      );
    }

    this.getAllSongsForAuthor(this.authorId);
  }

  getAllSongsForAuthor(authorId:number){
    this.songService.getSongsByAuthorId(authorId).subscribe(
      songs=>{
        this.songs=songs;
      },err=>{
        console.log(err);
      }
    )
  }

  redirectToAuthorList(){
    this.router.navigate(['/author'])
  }

  redirectToSongPage(songId:number){
    if(songId>0) {
      this.router.navigate(['/song/songPage', songId]);
    }
  }
}
