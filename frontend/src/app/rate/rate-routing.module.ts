import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RateCreateComponent} from "./rate-create/rate-create.component";

const routes: Routes = [
  {path:'rate/createRate/:songId',component:RateCreateComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RateRoutingModule { }
