export class Song{

  songId:number;
  songName: string;
  musicGenre:string;
  releaseYear: number;
  youTubeLink:string;
  authorId:number;
  songAverageRate:number;


  constructor(songId: number, songName: string, musicGenre: string, releaseYear: number, youTubeLink: string, authorId: number, songAverageRate:number){
    this.songId = songId;
    this.songName = songName;
    this.musicGenre = musicGenre;
    this.releaseYear = releaseYear;
    this.youTubeLink = youTubeLink;
    this.authorId=authorId;
    this.songAverageRate=songAverageRate;
  }
}
