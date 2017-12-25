import { Component, OnInit } from '@angular/core';
import {AuthorService} from "../author.service";
import {Router} from "@angular/router";
import {Author} from "../author";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.css'],
  providers: [AuthorService]
})
export class AuthorListComponent implements OnInit {

  private authors: Author[];
  authorSearchForm: FormGroup;
  sortedByName: boolean;
  sortedByYearOfBirth: boolean;
  sortedByCountry: boolean;

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
  }

  getAllAuthors(){
    this.authorService.getAllAuthors().subscribe(
      authors=>{
        this.authors=authors;
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
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByName(){
    this.authorService.getAllAuthorsSortedByName().subscribe(
      authors =>{
        this.sortedByName=true;
        console.log(this.sortedByName);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByNameReversed(){
    this.authorService.getAllAuthorsSortedByNameReversed().subscribe(
      authors =>{
        this.sortedByName=false;
        console.log(this.sortedByName);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByYearOfBirth(){
    this.authorService.getAllAuthorsSortedByYearOfBirth().subscribe(
      authors =>{
        this.sortedByYearOfBirth=true;
        console.log(this.sortedByYearOfBirth);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByYearOfBirthReversed(){
    this.authorService.getAllAuthorsSortedByYearOfBirthReversed().subscribe(
      authors =>{
        this.sortedByYearOfBirth=false;
        console.log(this.sortedByYearOfBirth);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByCountryOfOrigin(){
    this.authorService.getAllAuthorsSortedByCountryOfOrigin().subscribe(
      authors =>{
        this.sortedByCountry=true;
        console.log(this.sortedByCountry);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }

  getAllAuthorsSortedByCountryOfOriginReversed(){
    this.authorService.getAllAuthorsSortedByCountryOfOriginReversed().subscribe(
      authors =>{
        this.sortedByCountry=false;
        console.log(this.sortedByCountry);
        this.authors = authors},
      err=>{
        console.log(err)
      }
    );
  }
}
