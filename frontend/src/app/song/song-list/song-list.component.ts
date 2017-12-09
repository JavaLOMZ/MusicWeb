import { Component, OnInit } from '@angular/core';
import {Song} from "../song";
import {songNameTaken, SongService} from "../song.service";
import {Router} from "@angular/router";
import {User} from "../../user/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css'],
  providers:[SongService]
})
export class SongListComponent implements OnInit {

  songs:Song[];
  songSearchForm: FormGroup;

  constructor(private router:Router,
              private songService:SongService
  ) { }

  ngOnInit() {
    this.songSearchForm = new FormGroup({
      searchWord: new FormControl('',[Validators.required]),
    });
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
          this.router.navigate(['/song']);
          console.log('done');
        }
      );
      window.location.reload();
    }
  }

  redirectSingleSongPage(songId : number) {
    if(songId) {
      this.router.navigate(['/song/songPage', songId]);
    }
  }

  onSubmit() {
    if (this.songSearchForm.valid) {
      this.getSongsBySearchWord();
    }
  }

    getSongsBySearchWord(){
      this.songService.getSongsBySearchWord(this.songSearchForm.controls['searchWord'].value).subscribe(
        songs =>{
          this.songs = songs},
        err=>{
          console.log(err)
        }
      );
    }
}
