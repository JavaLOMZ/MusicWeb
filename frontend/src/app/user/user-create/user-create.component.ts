import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css'],
  providers: [UserService]
})
export class UserCreateComponent implements OnInit {

  userId: number;
  user: User;

  userForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.userForm=new FormGroup({
      nickname: new FormControl('',Validators.required),
      email: new FormControl('',Validators.required),
      isAdmin: new FormControl('',Validators.required),
      isBanned:new FormControl('', Validators.required)
    })
  }

  ngOnDeastroy(): void{
  }

  onSubmit(){
    if(this.userForm.valid){
      let user:User=new User(null,
        this.userForm.controls['nickname'].value,
        this.userForm.controls['email'].value,
        this.userForm.controls['isAdmin'].value,
        this.userForm.controls['isBanned'].value);
      this.userService.createOrUpdateUser(user).subscribe();
    }
    this.userForm.reset();
    this.router.navigate(['/user']);
  }
  redirectUserPage(){
    this.router.navigate(['/user']);
  }

}
