import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorListComponent} from "./author-list/author-list.component";
import {AuthorCreateComponent} from "./author-create/author-create.component";
import {AuthorPageComponent} from "./author-page/author-page.component";

const routes: Routes = [
  {path:'author',component:AuthorListComponent},
  {path:'author/create',component:AuthorCreateComponent},
  {path:'author/authorPage',component:AuthorPageComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthorRoutingModule { }
