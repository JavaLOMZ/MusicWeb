import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SongCreateComponent} from "./song-create/song-create.component";
import {SongPageComponent} from "./song-page/song-page.component";
import {SongListComponent} from "./song-list/song-list.component";
import {CanActivateAuthGuard} from "../can-active.authguard";
import {RateCreateComponent} from "../rate/rate-create/rate-create.component";
import {RateModule} from "../rate/rate.module";

const routes: Routes = [
  {path:'song',component:SongListComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'song/create',component:SongCreateComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'song/songPage/:songId',component:SongPageComponent,  canActivate: [CanActivateAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  declarations:[RateCreateComponent],
  exports: [RouterModule]
})
export class SongRoutingModule { }
