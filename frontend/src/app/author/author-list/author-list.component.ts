import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../author.service";
import {Router} from "@angular/router";
import {Author} from "../author";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {nextTick} from "q";

@Component({
  selector: 'app-author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.css'],
  providers: [AuthorService]
})
export class AuthorListComponent implements OnInit {

  authors: Author[];

  authorSearchForm: FormGroup;
  sortedByName: boolean;
  sortedByYearOfBirth: boolean;
  sortedByCountry: boolean;
  searchedWord: string;
  averageAuthorRates: number[];
  authorRate:number;

  constructor(private router:Router,
              private authorService:AuthorService) { }

  ngOnInit() {
    this.authorSearchForm = new FormGroup({
      searchWord: new FormControl('',[Validators.required]),
    });
    this.getAllAuthors();
    this.sortedByName=false;
    this.sortedByYearOfBirth=false;
    this.sortedByCountry=false;
    //todo how to show it in html?
    this.getAverageRatesForAllAuthors(null,null);
  }

  getAllAuthors(){
    this.authorService.getAllAuthors().subscribe(
      authors=>{
        this.authors=authors
        this.searchedWord=null;
      }, err=>{
        console.log(err);
      }
    )
  }

  redirectNewAuthorPage() {
    this.router.navigate(['/author/create']);
  }

  editAuthorPage(author: Author) {
    if (author) {
      this.router.navigate(['/author/create', author])
    }
  }

  deleteAuthor(authorId: number) {
    if (authorId > 0) {
      this.authorService.deleteAuthorById(authorId).subscribe(
        res => {
          this.router.navigate(['/author']);
          console.log('done');
        }
      );
      window.location.reload();
    }
  }

  redirectToSingleAuthorPage(authorId: number){
    if(authorId) {
      this.router.navigate(['/author/authorPage', authorId]);
    }
  }

  redirectToUserPage(){
    this.router.navigate(['/user']);
  }

  redirectToSongCreatePage(authorId:number) {
    if (authorId > 0) {
      this.router.navigate(['/song/create', authorId]);
    }
  }

  onSubmit() {
    if (this.authorSearchForm.valid) {
      this.getAuthorBySearchWord();
    }
  }

  getAuthorBySearchWord(){
    this.authorService.getAuthorsBySearchWord(this.authorSearchForm.controls['searchWord'].value).subscribe(
      authors =>{
        this.authors = authors;
        this.searchedWord=this.authorSearchForm.controls['searchWord'].value;
        this.getAverageRatesForAllAuthors(null,this.authorSearchForm.controls['searchWord'].value)
      },
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByName(searchedWord: string){
    this.authorService.getAllAuthorsSortedByName(searchedWord).subscribe(
      authors =>{
        this.sortedByName=true;
        this.getAverageRatesForAllAuthors('name',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByNameReversed(searchedWord: string){
    this.authorService.getAllAuthorsSortedByNameReversed(searchedWord).subscribe(
      authors =>{
        this.sortedByName=false;
        this.getAverageRatesForAllAuthors('nameReversed',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByYearOfBirth(searchedWord: string){
    this.authorService.getAllAuthorsSortedByYearOfBirth(searchedWord).subscribe(
      authors =>{
        this.sortedByYearOfBirth=true;
        this.getAverageRatesForAllAuthors('yearOfBirth',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByYearOfBirthReversed(searchedWord: string){
    this.authorService.getAllAuthorsSortedByYearOfBirthReversed(searchedWord).subscribe(
      authors =>{
        this.sortedByYearOfBirth=false
        this.getAverageRatesForAllAuthors('yearOfBirthReversed',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByCountryOfOrigin(searchedWord: string){
    this.authorService.getAllAuthorsSortedByCountryOfOrigin(searchedWord).subscribe(
      authors =>{
        this.sortedByCountry=true;
        this.getAverageRatesForAllAuthors('countryOfOrigin',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByCountryOfOriginReversed(searchedWord: string){
    this.authorService.getAllAuthorsSortedByCountryOfOriginReversed(searchedWord).subscribe(
      authors =>{
        this.sortedByCountry=false;
        this.getAverageRatesForAllAuthors('countryOfOriginReversed',searchedWord);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }



  //todo te dwie metody daaja mozliwosc uzyskania chamskigo oceny autora obok w tabeli natomiast dobrze by bylo
  //zrobic wedlug mnie nowy widok, ktory by byl po prostu rankingiem autorow i tak samo z piosenkami
  //bedzie wtedy mozan to dopasowac, poniewaz jakby sortowac rowniez te oceny to mysle, ze duza roboty
  //z tymi metodami sortujacymi by byly bo oceny sie wyswietlaja wedlug domyslnego dodawania do bazy danych autorow
  //mozna zrobic, zeby ssie pokazywaly jako posortowane, a przekazac po prostu metode napisana w javie ktora wysle
  //obiekty autorow takze posortowane i wsio
  getAverageRatesForAllAuthors(howDoWeSortAuthors:string, searchWord:string) {
    this.authorService.getAverageRatesForAllAuthors(howDoWeSortAuthors,searchWord).subscribe(
      averageAuthorRates=>{
        this.averageAuthorRates=averageAuthorRates;
        console.log(averageAuthorRates);
      }, err=>{
        console.log(err);
      }
    );
  }

  getAuthorRate(){
    this.authorRate=this.averageAuthorRates.shift();
    return this.authorRate;
  }
}
