import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SongRoutingModule } from './song-routing.module';
import { SongCreateComponent } from './song-create/song-create.component';
import { SongPageComponent } from './song-page/song-page.component';
import { SongListComponent } from './song-list/song-list.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RateModule} from "../rate/rate.module";

@NgModule({
  imports: [
    CommonModule,
    SongRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RateModule
  ],
  declarations: [SongCreateComponent, SongPageComponent, SongListComponent]
})
export class SongModule { }
