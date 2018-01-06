import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {songNameTaken, SongService} from "../song.service";
import {Song} from "../song";
import {AuthorService} from "../../author/author.service";
import {Author} from "../../author/author";
import {MusicGenre} from "../music.genre";
import {Validator} from "codelyzer/walkerFactory/walkerFn";


@Component({
  selector: 'app-song-create',
  templateUrl: './song-create.component.html',
  styleUrls: ['./song-create.component.css'],
  providers: [SongService, AuthorService]
})
export class SongCreateComponent implements OnInit {

  songId: number;
  song: Song;
  authors: Author[];
  musicGenre: MusicGenre[];

  authorId:number;
  songAverageRate:number;
  songForm: FormGroup;
  songNameToShow:string;
  private sub: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private songService: SongService,
              private authorService: AuthorService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.songId = params['songId'],
      this.authorId=params['authorId'],
        this.songAverageRate=params['songAverageRate'];
    });
    if(this.songId) {
      this.songForm = new FormGroup({
        songName: new FormControl(),
        musicGenre: new FormControl('', Validators.required),
        releaseYear: new FormControl('', [Validators.required, Validators.max(2017)]),
        youTubeLink: new FormControl('', [Validators.pattern(/^((?:https?:)?\/\/)?((?:www|m)\.)?((?:youtube\.com|youtu.be))(\/(?:[\w\-]+\?v=|embed\/|v\/)?)([\w\-]+)(\S+)?$/)]),
        authorId: new FormControl('', Validators.required)
      });
    }
    else{
      this.songForm = new FormGroup({
        songName: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(100)], [songNameTaken(this.songService, this.authorId)]),
        musicGenre: new FormControl('', Validators.required),
        releaseYear: new FormControl('', [Validators.required, Validators.max(2017)]),
        youTubeLink: new FormControl('', [Validators.pattern(/^((?:https?:)?\/\/)?((?:www|m)\.)?((?:youtube\.com|youtu.be))(\/(?:[\w\-]+\?v=|embed\/|v\/)?)([\w\-]+)(\S+)?$/)]),
        authorId: new FormControl('', Validators.required)
      });
    }


    if (this.songId) {
      this.songService.getSongById(this.songId).subscribe(
        song => {
          this.songId = song.songId;
          this.songNameToShow=song.songName;
          this.songForm.patchValue({
            songName: song.songName,
            musicGenre: song.musicGenre,
            releaseYear: song.releaseYear,
            youTubeLink: song.youTubeLink,
            authorId: this.authorId,
          });
        }, error => {
          console.log(error);
        }
      );
    }
    this.getAllAuthors();
    this.getMusicGenreTypes();

  }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }


  onSubmit() {
    if (this.songForm.valid) {
        if (this.songId) {
          let song: Song = new Song(this.songId,
            this.songForm.controls['songName'].value,
            this.songForm.controls['musicGenre'].value,
            this.songForm.controls['releaseYear'].value,
            this.songForm.controls['youTubeLink'].value,
            this.songForm.controls['authorId'].value,
            this.songAverageRate);
          this.songService.createOrUpdateSong(song).subscribe();
        } else {
          let song: Song = new Song(null,
            this.songForm.controls['songName'].value,
            this.songForm.controls['musicGenre'].value,
            this.songForm.controls['releaseYear'].value,
            this.songForm.controls['youTubeLink'].value,
            this.songForm.controls['authorId'].value,
            0);
          this.songService.createOrUpdateSong(song).subscribe();
        }
        this.songForm.reset();
        this.router.navigate(['/song']);
      }
    window.location.reload();
    }


  redirectSongListPage() {
    this.router.navigate(['/song']);
  }

  getAllAuthors() {
    this.authorService.getAllAuthors().subscribe(
      authors => {
        this.authors = authors;
      }, err => {
        console.log(err);
      }
    )
  }

  getMusicGenreTypes() {
    this.songService.getMusicGenreTypes().subscribe(
      musicGenre => {
        this.musicGenre = musicGenre;
      }, err => {
        console.log(err);
      }
    )
  }

  redirectToSingleAuthorPage(authorId: number){
    if(authorId) {
      this.router.navigate(['/author/authorPage', authorId]);
    }
  }
}
