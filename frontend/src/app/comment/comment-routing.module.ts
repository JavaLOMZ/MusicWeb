import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CommentCreateComponent} from "./comment-create/comment-create.component";
import {CanActivateAuthGuard} from "../can-active.authguard";


const routes: Routes = [
  {path:'comment/createComment/:songId',component:CommentCreateComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'comment/createComment',component:CommentCreateComponent,  canActivate: [CanActivateAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommentRoutingModule { }
