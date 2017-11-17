import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SongCreateComponent} from "./song-create/song-create.component";
import {SongPageComponent} from "./song-page/song-page.component";
import {SongListComponent} from "./song-list/song-list.component";
import {CanActivateAuthGuard} from "../can-active.authguard";


const routes: Routes = [
  {path:'song',component:SongListComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'song/create',component:SongCreateComponent,  canActivate: [CanActivateAuthGuard]},
  {path:'song/songPage/:songId',component:SongPageComponent,  canActivate: [CanActivateAuthGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SongRoutingModule { }
