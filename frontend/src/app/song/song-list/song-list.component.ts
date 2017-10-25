import { Component, OnInit } from '@angular/core';
import {Song} from "../song";
import {SongService} from "../song.service";
import {Router} from "@angular/router";
import {User} from "../../user/user";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css'],
  providers:[SongService]
})
export class SongListComponent implements OnInit {

  songs:Song[];

  constructor(private router:Router,
              private songService:SongService
  ) { }

  ngOnInit() {
    this.getAllSongs();
  }

  getAllSongs(){
    this.songService.getAllSongs().subscribe(
      songs=>{
        this.songs=songs;
      },
      err=>{
        console.log(err);
      }
    );
  }

  redirectNewSongPage() {
    this.router.navigate(['/song/create']);
  }

  editSongPage(song: Song) {
    if (song) {
      this.router.navigate(['/song/create', song])
    }
  }

  deleteSong(songId: number) {
    if (songId > 0) {
      this.songService.deleteSongById(songId).subscribe(
        res => {
          this.getAllSongs();
          this.router.navigate(['/song']);
          console.log('done');
        }
      );
      window.location.reload();
    }
  }


  redirectSingeSongPage(songId : number) {
    if(songId) {
      this.router.navigate(['/song/songPage', songId]);
    }
  }

  redirectToCreateComment(songId: number){
    if(songId){
      this.router.navigate(['/comment/createComment',songId])
    }
  }

  redirectToCreateRate(songId: number){
    if(songId){
      this.router.navigate(['/rate/createRate',songId])
    }
  }
}
