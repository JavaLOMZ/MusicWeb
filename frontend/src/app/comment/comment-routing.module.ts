import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CommentCreateComponent} from "./comment-create/comment-create.component";


const routes: Routes = [
  {path:'comment/createComment/:songId',component:CommentCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommentRoutingModule { }
