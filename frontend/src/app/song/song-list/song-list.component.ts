import {Component, OnInit} from '@angular/core';
import {Song} from "../song";
import {songNameTaken, SongService} from "../song.service";
import {Router} from "@angular/router";
import {User} from "../../user/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthorService} from "../../author/author.service";
import {Author} from "../../author/author";

@Component({
  selector: 'app-song-list',
  templateUrl: './song-list.component.html',
  styleUrls: ['./song-list.component.css'],
  providers: [SongService, AuthorService]
})
export class SongListComponent implements OnInit {

  songs: Song[];
  songSearchForm: FormGroup;
  searchedWord: string;

  sortedBySongName: boolean;
  sortedByMusicGenre:boolean;
  sortedByReleaseYear: boolean;
  sortedByAuthorName: boolean;
  sortedByAverageRate: boolean;


  authors: Author[];
  authorName:string;
  constructor(private router: Router,
              private songService: SongService,
              private authorService: AuthorService) {
  }

  ngOnInit() {
    this.songSearchForm = new FormGroup({
      searchWord: new FormControl('', [Validators.required]),
    });
    this.getAllSongs();
    this.getAllAuthors();
    this.sortedBySongName=false;
    this.sortedByMusicGenre=false;
    this.sortedByReleaseYear=false;
    this.sortedByAuthorName=false;
    this.sortedByAverageRate=false;
  }

  getAllSongs() {
    this.songService.getAllSongs().subscribe(
      songs => {
        this.songs = songs
        this.searchedWord=null;
      },
      err => {
        console.log(err)
      }
    );
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

  redirectSingleSongPage(songId: number) {
    if (songId) {
      this.router.navigate(['/song/songPage', songId]);
    }
  }

  onSubmit() {
    if (this.songSearchForm.valid) {
      this.getSongsBySearchWord();
    }
  }

  getSongsBySearchWord() {
    this.songService.getSongsBySearchWord(this.songSearchForm.controls['searchWord'].value).subscribe(
      songs => {
        this.songs = songs
        this.searchedWord=this.songSearchForm.controls['searchWord'].value
      },
      err => {
        console.log(err)
      }
    );
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

  getAllSongsSortedBySongName(searchedWord:string){
    this.songService.getAllSongsSortedBySongName(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedBySongName=true;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedBySongNameReversed(searchedWord:string){
    this.songService.getAllSongsSortedBySongNameReversed(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedBySongName=false;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByMusicGenre(searchedWord:string){
    this.songService.getAllSongsSortedByMusicGenre(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByMusicGenre=true;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByMusicGenreReversed(searchedWord:string){
    this.songService.getAllSongsSortedByMusicGenreReversed(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByMusicGenre=false;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByReleaseYear(searchedWord:string){
    this.songService.getAllSongsSortedByReleaseYear(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByReleaseYear=true;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByReleaseYearReversed(searchedWord:string){
    this.songService.getAllSongsSortedByReleaseYearReversed(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByReleaseYear=false;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByAuthorName(searchedWord:string){
    this.songService.getAllSongsSortedByAuthorName(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByAuthorName=true;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByAuthorNameReversed(searchedWord:string){
    this.songService.getAllSongsSortedByAuthorNameReversed(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByAuthorName=false;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByAverageRate(searchedWord:string){
    this.songService.getAllSongsSortedByAverageRate(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByAverageRate=true;
      },err=>{
        console.log(err)
      }
    )
  }

  getAllSongsSortedByAverageRateReversed(searchedWord:string){
    this.songService.getAllSongsSortedByAverageRateReversed(searchedWord).subscribe(
      songs=>{
        this.songs=songs;
        this.sortedByAverageRate=false;
      },err=>{
        console.log(err)
      }
    )
  }
}
