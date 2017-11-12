import { Component, OnInit } from '@angular/core';
import {CommentService} from "../comment.service";
import {SongService} from "../../song/song.service";
import {UserService} from "../../user/user.service";
import {Song} from "../../song/song";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../user/user";
import {ActivatedRoute, Router} from "@angular/router";
import {CommentOur} from "../comment";
import {AuthenticationService} from "../../authentication.service";


@Component({
  selector: 'app-comment-create',
  templateUrl: './comment-create.component.html',
  styleUrls: ['./comment-create.component.css'],
  providers:[CommentService, SongService, UserService]
})
export class CommentCreateComponent implements OnInit {

  commentId: number;
  songId:number;
  comment: CommentOur;
  userId: number;

  commentForm: FormGroup;
  private sub:any;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private commentService:CommentService,
              private songService: SongService,
              private userService: UserService,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.commentId=params['commentId'],
        this.songId=params['songId'];
    });


    this.commentForm=new FormGroup({
      commentText: new FormControl('',Validators.required),
      songId:new FormControl('',Validators.required),
      userId:new FormControl('',Validators.required)
    });

    if(this.commentId){
      this.commentService.getCommentById(this.commentId).subscribe(
        comment=>{
          this.commentId=comment.commentId;
          this.commentForm.patchValue({
            commentText: comment.commentText,
            userId: comment.userId,
            songId:this.songId
          });
        }, error=>{
          console.log(error);
        }
      );
    }
    this.getUserId();
  }

  ngOnDestroy(): void{
    this.sub.unsubscribe();
  }

  onSubmit(){
    if(this.commentForm.valid){
      if(this.commentId) {
        let comment: CommentOur = new CommentOur(this.commentId,
          this.commentForm.controls['commentText'].value,
          this.commentForm.controls['songId'].value,
          this.commentForm.controls['userId'].value);
        this.commentService.createOrUpdateComment(comment).subscribe();
      }else {
        let comment: CommentOur = new CommentOur(null,
          this.commentForm.controls['commentText'].value,
          this.commentForm.controls['songId'].value,
          this.commentForm.controls['userId'].value);
        this.commentService.createOrUpdateComment(comment).subscribe();
      }
    }
    this.commentForm.reset();
    this.router.navigate(['/song/songPage/'+ this.songId]);
    window.location.reload();
  }

  redirectSongListPage(){
    this.router.navigate(['/song']);
  }

  getUserId() {
    this.userService.getUserByUsername(this.authenticationService.getUsername()).subscribe(
      user => {
        this.userId = user.userId;
      }, err => {
        console.log(err);
      }
    )
  }
}
