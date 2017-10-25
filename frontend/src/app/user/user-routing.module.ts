import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {UserCreateComponent} from "./user-create/user-create.component";
import {UserPageComponent} from "./user-page/user-page.component";


const routes: Routes = [
  {path:'user',component:UserListComponent},
  {path:'user/create',component:UserCreateComponent},
  {path:'user/userPage/:userId',component: UserPageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
