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

  constructor(private router:Router,
              private authorService:AuthorService) { }

  ngOnInit() {
    this.authorSearchForm = new FormGroup({
      searchWord: new FormControl('',[Validators.required]),
    });
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

}
