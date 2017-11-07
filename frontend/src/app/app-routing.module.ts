import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserListComponent} from "./user/user-list/user-list.component";
import {CanActivateAuthGuard} from "./can-active.authguard";
import {LoginPageComponent} from "./login/login-page/login-page.component";

const routes: Routes = [
  {path:'',component: LoginPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
