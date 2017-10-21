import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers:[UserService]
})
export class UserListComponent implements OnInit {

  private users:User[];

  constructor( private router: Router,
    private userService: UserService) { }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers(){
    this.userService.getAllUsers().subscribe(
      users=>{
        this.users=users;
      },
      err=>{
        console.log(err);
      }
    );
  }

  redirectNewUserPage(){
    this.router.navigate(['/user/create']);
  }

  deleteUser(userId:number){
    if(userId>0){
      this.userService.deleteUserById(userId).subscribe(
        res=>{
          this.getAllUsers();
          this.router.navigate(['/user']);
          console.log('done');
        }
      );
    }
  }



}
