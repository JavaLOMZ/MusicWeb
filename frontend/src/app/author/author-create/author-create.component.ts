import { Component, OnInit } from '@angular/core';
import {User} from "../../user/user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Author} from "../author";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../user/user.service";
import {authorNameTaken, AuthorService} from "../author.service";

@Component({
  selector: 'app-author-create',
  templateUrl: './author-create.component.html',
  styleUrls: ['./author-create.component.css'],
  providers:[AuthorService]
})
export class AuthorCreateComponent implements OnInit {

  authorId: number;
  author: Author;

  authorForm: FormGroup;
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authorService: AuthorService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.authorId=params['authorId']
  });

    this.authorForm=new FormGroup({
      name: new FormControl('',[Validators.required, Validators.maxLength(25)], [authorNameTaken(this.authorService)]),
      yearOfBirth: new FormControl('',[Validators.required, Validators.min(1000), Validators.max(2017)]),
      countryOfOrigin:new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(25)])
    });


    if(this.authorId){
      this.authorService.getAuthorById(this.authorId).subscribe(
        author=>{
          this.authorId=author.authorId;
          this.authorForm.patchValue({
            name: author.name,
            yearOfBirth: author.yearOfBirth,
            countryOfOrigin: author.countryOfOrigin
          });
        }, error=>{
          console.log(error);
        }
      );
    }
  }

  ngOnDestroy(): void{
    this.sub.unsubscribe();
  }

  onSubmit(){
    if(this.authorForm.valid){
      if(this.authorId) {
        let author: Author = new Author(this.authorId,
          this.authorForm.controls['name'].value,
          this.authorForm.controls['yearOfBirth'].value,
          this.authorForm.controls['countryOfOrigin'].value);
        this.authorService.createOrUpdateAuthor(author).subscribe();
      }else {
        let author: Author = new Author(null,
          this.authorForm.controls['name'].value,
          this.authorForm.controls['yearOfBirth'].value,
          this.authorForm.controls['countryOfOrigin'].value);
        this.authorService.createOrUpdateAuthor(author).subscribe();
      }
    }


    this.authorForm.reset();
    this.router.navigate(['/author']);
    window.location.reload();
  }
  redirectAuthorListPage(){
    this.router.navigate(['/author']);
  }

}
