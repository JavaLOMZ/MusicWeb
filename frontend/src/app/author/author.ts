export class Author{

  authorId:number;
  name:string;
  yearOfBirth:number;
  countryOfOrigin:string;
  authorAverageRate:number;


  constructor(authorId: number, name: string, yearOfBirth: number, countryOfOrigin: string, authorAverageRate:number) {
    this.authorId = authorId;
    this.name = name;
    this.yearOfBirth = yearOfBirth;
    this.countryOfOrigin = countryOfOrigin;
    this.authorAverageRate=authorAverageRate;
  }
}
