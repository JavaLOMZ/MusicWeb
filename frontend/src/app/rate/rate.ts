export class Rate {
  rateId: number;
  rateValue: number;
  songId: number;
  userId: number;


  constructor(rateId: number, rateValue: number, songId: number, userId: number) {
    this.rateId = rateId;
    this.rateValue = rateValue;
    this.songId = songId;
    this.userId = userId;
  }
}
