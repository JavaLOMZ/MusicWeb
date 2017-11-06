import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {UserCreateComponent} from "./user-create/user-create.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {CanActivateAuthGuard} from "../can-active.authguard";


const routes: Routes = [
  {path:'user',component: UserListComponent, canActivate: [CanActivateAuthGuard]},
  // {path:'user/create',component: UserCreateComponent, canActivate: [CanActivateAuthGuard]},
  {path:'user/create',component: UserCreateComponent},
  {path:'user/userPage/:userId',component: UserPageComponent, canActivate: [CanActivateAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
