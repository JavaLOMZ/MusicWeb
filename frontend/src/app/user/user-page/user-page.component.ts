import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
  providers:[UserService]
})
export class UserPageComponent implements OnInit {

  userId: number;
  user: User;
  // nickname:string;
  // email:string;
  comments:Comment[];
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.userId=params['userId'];
        //,this.nickname=params['nickname'],this.email=params['email'];

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

    this.getAllCommentsFromUser(this.userId);
  }

  getAllCommentsFromUser(userId:number){
    this.userService.getAllCommentsFromUser(userId).subscribe(
      comments => {
        this.comments=comments;
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
