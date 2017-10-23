export class Author{

  authorId:number;
  name:string;
  yearOfBirth:number;
  countryOfOrigin:string;


  constructor(authorId: number, name: string, yearOfBirth: number, countryOfOrigin: string) {
    this.authorId = authorId;
    this.name = name;
    this.yearOfBirth = yearOfBirth;
    this.countryOfOrigin = countryOfOrigin;
  }
}
