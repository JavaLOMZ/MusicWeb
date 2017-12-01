import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommentRoutingModule } from './comment-routing.module';
import { CommentCreateComponent } from './comment-create/comment-create.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    CommentRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[CommentCreateComponent],
  declarations: [CommentCreateComponent]
})
export class CommentModule { }
