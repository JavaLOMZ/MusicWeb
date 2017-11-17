import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RateCreateComponent} from "./rate-create/rate-create.component";
import {CanActivateAuthGuard} from "../can-active.authguard";

const routes: Routes = [
  {path:'rate/createRate',component:RateCreateComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'rate/createRate/:songId',component:RateCreateComponent,  canActivate: [CanActivateAuthGuard]}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RateRoutingModule { }
