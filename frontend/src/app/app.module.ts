import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {UserModule} from "./user/user.module";
import {HttpModule} from "@angular/http";
import {AuthorModule} from "./author/author.module";
import {CommentModule} from "./comment/comment.module";
import {SongModule} from "./song/song.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UserModule,
    AuthorModule,
    CommentModule,
    SongModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
