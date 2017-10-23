import {Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers: [UserService]
})
export class UserListComponent implements OnInit {

  private users: User[];
  //private user: User;

  constructor(private router: Router,
              private userService: UserService) {
  }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe(
      users => {
        this.users = users;
      },
      err => {
        console.log(err);
      }
    );
  }

  redirectNewUserPage() {
    this.router.navigate(['/user/create']);
  }

  editUserPage(user: User) {
    if (user) {
      this.router.navigate(['/user/create', user])
    }
  }

  deleteUser(userId: number) {
    if (userId > 0) {
      this.userService.deleteUserById(userId).subscribe(
        res => {
          this.getAllUsers();
          this.router.navigate(['/user']);
          console.log('done');
        }
      );
      window.location.reload();
    }
  }

  redirectSingeUserPage(user:User) {
    if(user) {
      this.router.navigate(['/user/userPage', user]);
    }
  }


}
