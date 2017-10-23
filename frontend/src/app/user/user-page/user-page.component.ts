import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
  providers: [UserService]
})
export class UserPageComponent implements OnInit {

  private userId: number;
  private sub: any;
  private comments: Comment[];

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.userId = params['userId'];
    });
  this.getAllCommentsFromUser(this.userId);
  }

  getAllCommentsFromUser(userId: number) {
    this.userService.getAllCommentsFromUser(userId).subscribe(
      comments => {
        this.comments=comments;
      },
      err => {
        console.log(err);
      }
    );
  }
}
