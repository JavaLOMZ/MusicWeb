import { Component, OnInit } from '@angular/core';
import {UserService} from "../../user/user.service";
import {SongService} from "../../song/song.service";
import {User} from "../../user/user";
import {Rate} from "../rate";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RateService} from "../rate.service";

@Component({
  selector: 'app-rate-create',
  templateUrl: './rate-create.component.html',
  styleUrls: ['./rate-create.component.css'],
  providers: [UserService, SongService, RateService]
})
export class RateCreateComponent implements OnInit {

  rateId: number;
  songId:number;
  rate: Rate;
  users:User[];

  rateForm: FormGroup;
  private sub:any;
  constructor(private route: ActivatedRoute,
              private router: Router,
              private rateService:RateService,
              private songService: SongService,
              private userService: UserService) { }

  ngOnInit() {
    this.sub=this.route.params.subscribe(params=>{
      this.rateId=params['rateId'],
        this.songId=params['songId'];
    });


    this.rateForm=new FormGroup({
      rateValue: new FormControl('',Validators.required),
      songId:new FormControl('',Validators.required),
      userId:new FormControl('',Validators.required)
    });

    if(this.rateId){
      this.rateService.getRateById(this.rateId).subscribe(
        rate=>{
          this.rateId=rate.rateId;
          this.rateForm.patchValue({
            rateValue: rate.rateValue,
            userId: rate.userId,
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
    if(this.rateForm.valid){
      if(this.rateId) {
        let rate: Rate = new Rate(this.rateId,
          this.rateForm.controls['rateValue'].value,
          this.rateForm.controls['songId'].value,
          this.rateForm.controls['userId'].value);
        this.rateService.createOrUpdateRate(rate).subscribe();
      }else {
        let rate: Rate = new Rate(null,
          this.rateForm.controls['rateValue'].value,
          this.rateForm.controls['songId'].value,
          this.rateForm.controls['userId'].value);
        this.rateService.createOrUpdateRate(rate).subscribe();
      }
    }
    this.rateForm.reset();
    //todo it ll be nice to redirecct straight into rateed song
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
