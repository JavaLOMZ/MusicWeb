import { Component, OnInit } from '@angular/core';
import {CommentService} from "../comment.service";
import {SongService} from "../../song/song.service";
import {UserService} from "../../user/user.service";
import {Song} from "../../song/song";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../user/user";
import {ActivatedRoute, Router} from "@angular/router";
import {CommentOur} from "../comment";


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
  users:User[];

  commentForm: FormGroup;
  private sub:any;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private commentService:CommentService,
              private songService: SongService,
              private userService: UserService) { }

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

    // this.getAllSongs();
    this.getAllUsers();
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
    //todo it ll be nice to redirecct straight into commented song
    this.router.navigate(['/song']);
    window.location.reload();
  }

  redirectSongListPage(){
    this.router.navigate(['/song']);
  }

  getAllUsers(){
    this.userService.getAllUsers().subscribe(
      users=>{
        this.users=users;
      },err=>{
        console.log(err);
      }
    )
  }
}
