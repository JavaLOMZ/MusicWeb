import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SongCreateComponent} from "./song-create/song-create.component";
import {SongPageComponent} from "./song-page/song-page.component";
import {SongListComponent} from "./song-list/song-list.component";

const routes: Routes = [
  {path:'song',component:SongListComponent},
  {path:'song/create',component:SongCreateComponent},
  {path:'song/songPage/:songId',component:SongPageComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SongRoutingModule { }
