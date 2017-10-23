import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorListComponent} from "./author-list/author-list.component";
import {AuthorCreateComponent} from "./author-create/author-create.component";

const routes: Routes = [
  {path:'author',component:AuthorListComponent},
  {path:'author/create',component:AuthorCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthorRoutingModule { }
