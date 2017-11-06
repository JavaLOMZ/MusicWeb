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
  private sub:any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.userId=params['userId'];
    });

    this.userForm=new FormGroup({
      nickname: new FormControl('',Validators.required),
      password:new FormControl('',Validators.required),
      email: new FormControl('',Validators.required),
    });


    if(this.userId){
      this.userService.getUserById(this.userId).subscribe(
        user=>{
          this.userId=user.userId;
          this.userForm.patchValue({
            nickname: user.nickname,
            password: user.password,
            email: user.email,
          });
        }, error=>{
          console.log(error);
          this.router.navigate(['login']);
          console.error("Error in UserListPage, redirecting to login");
        }
      );
    }
  }


  ngOnDestroy(): void{
    this.sub.unsubscribe();
  }



  onSubmit(){
    if(this.userForm.valid){
      if(this.userId) {
        let user: User = new User(this.userId,
          this.userForm.controls['nickname'].value,
          this.userForm.controls['password'].value,
          this.userForm.controls['email'].value);
        this.userService.createOrUpdateUser(user).subscribe();
      }else {
        let user: User = new User(null,
          this.userForm.controls['nickname'].value,
          this.userForm.controls['password'].value,
          this.userForm.controls['email'].value);
        this.userService.createOrUpdateUser(user).subscribe();
      }
    }
    this.userForm.reset();
    this.router.navigate(['/user']);
    window.location.reload();
  }

  redirectUserListPage(){
    this.router.navigate(['/user']);
  }


}
