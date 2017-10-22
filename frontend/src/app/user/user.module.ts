import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserListComponent } from './user-list/user-list.component';
import { UserCreateComponent } from './user-create/user-create.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { UserPageComponent } from './user-page/user-page.component';

@NgModule({
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [UserListComponent, UserCreateComponent, UserPageComponent]
})


export class UserModule {

}


