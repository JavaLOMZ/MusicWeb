import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RateRoutingModule } from './rate-routing.module';
import { RateCreateComponent } from './rate-create/rate-create.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    RateRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[RateCreateComponent],
  declarations: [RateCreateComponent]
})
export class RateModule { }
