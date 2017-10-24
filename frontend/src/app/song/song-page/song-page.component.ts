import { Component, OnInit } from '@angular/core';
import {SongService} from "../song.service";
import {CommentService} from "../../comment/comment.service";
import {Song} from "../song";
import {ActivatedRoute, Router} from "@angular/router";
import {CommentOur} from "../../comment/comment";


@Component({
  selector: 'app-song-page',
  templateUrl: './song-page.component.html',
  styleUrls: ['./song-page.component.css'],
  providers:[SongService, CommentService]
})
export class SongPageComponent implements OnInit {

  songId:number;
  song:Song;

  comments:CommentOur[];
  private sub:any;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private songService: SongService,
              private commentService: CommentService) { }

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


    this.getAllCommentsForSong(this.songId);
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

  redirectToSongList(){
    this.router.navigate(['/song'])
  }

}
