
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CategoryInputComponent } from './category-input/category-input.component';
import { CategoryDisplayComponent } from './category-display/category-display.component';
import { CategoryEditComponent } from './category-edit/category-edit.component';
import { CategoryWrapperComponent } from './category-wrapper/category-wrapper.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CategoryInputComponent,
    CategoryDisplayComponent,
    CategoryEditComponent,
    CategoryWrapperComponent,
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
