import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthorRoutingModule } from './author-routing.module';
import { AuthorListComponent } from './author-list/author-list.component';
import { AuthorCreateComponent } from './author-create/author-create.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    AuthorRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [AuthorListComponent, AuthorCreateComponent]
})
export class AuthorModule { }
