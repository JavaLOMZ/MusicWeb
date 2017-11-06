import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorListComponent} from "./author-list/author-list.component";
import {AuthorCreateComponent} from "./author-create/author-create.component";
import {AuthorPageComponent} from "./author-page/author-page.component";
import {CanActivateAuthGuard} from "../can-active.authguard";

const routes: Routes = [
  {path:'author',component:AuthorListComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'author/create',component:AuthorCreateComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'author/authorPage/:authorId',component:AuthorPageComponent,  canActivate: [CanActivateAuthGuard]}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthorRoutingModule { }
