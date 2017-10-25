import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import {CommentService} from "../../comment/comment.service";
import {CommentOur} from "../../comment/comment";
import {RateService} from "../../rate/rate.service";
import {Rate} from "../../rate/rate";
import {SongService} from "../../song/song.service";
import {Song} from "../../song/song";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
  providers:[UserService, CommentService, RateService]
})
export class UserPageComponent implements OnInit {

  userId: number;
  user: User;
  rates:Rate[];
  comments:CommentOur[];
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private rateService: RateService,
              private commentService: CommentService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.userId=params['userId'];
    });

    if(this.userId) {
      this.userService.getUserById(this.userId).subscribe(
        user => {
          this.user = user;
        }, error => {
          console.log(error);
        }
      );
    }
    //todo method which will get songs to combine them with Comments like Comment -> SongName not only songId
    this.getAllRatesFromUser(this.userId);
    this.getAllCommentsFromUser(this.userId);
  }

  getAllCommentsFromUser(userId:number){
    this.commentService.getAllCommentsFromUser(userId).subscribe(
      comments => {
        this.comments=comments;
      },
      err => {
        console.log(err);
      }
    );
  }

  getAllRatesFromUser(userId:number){
    this.rateService.getAllRatesFromUser(this.userId).subscribe(
      rates => {
        this.rates=rates;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectToUserList(){
    this.router.navigate(['/user'])
  }

}
