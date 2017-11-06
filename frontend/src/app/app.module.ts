import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {UserModule} from "./user/user.module";
import {HttpModule} from "@angular/http";
import {AuthorModule} from "./author/author.module";
import {CommentModule} from "./comment/comment.module";
import {SongModule} from "./song/song.module";
import {RateModule} from "./rate/rate.module";
import {LoginModule} from "./login/login.module";
import {AuthenticationService} from "./authentication.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CanActivateAuthGuard} from "./can-active.authguard";

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
    RateModule,
    SongModule,
    HttpModule,
    LoginModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthenticationService, CanActivateAuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
