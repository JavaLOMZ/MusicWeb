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
  sortedByAverageRate: boolean;
  searchedWord: string;

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
    this.sortedByAverageRate=false;
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
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByAverageRate(searchedWord: string){
    this.authorService.getAllAuthorsSortedByAverageRate(searchedWord).subscribe(
      authors =>{
        this.sortedByAverageRate=true;
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }


  getAllAuthorsSortedByAverageRateReversed(searchedWord: string){
    this.authorService.getAllAuthorsSortedByAverageRateReversed(searchedWord).subscribe(
      authors =>{
        this.sortedByAverageRate=false;
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }
}
