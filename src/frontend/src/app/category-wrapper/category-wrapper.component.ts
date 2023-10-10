import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoryDisplayComponent } from '../category-display/category-display.component';
import { CategoryEditComponent } from '../category-edit/category-edit.component';

import {Category} from "../category";

@Component({
  selector: 'app-category-wrapper',
  templateUrl: './category-wrapper.component.html',
  styleUrls: ['./category-wrapper.component.css'],
  standalone: true,
  imports: [CategoryDisplayComponent, CategoryEditComponent, CommonModule]
})
export class CategoryWrapperComponent {

  @Input() category: Category = new Category(0, "", "");
  @Output() removeItemEvent = new EventEmitter();
  editable: boolean = false;

  handleEditClick(): void {
    this.editable = true;
  }

  handleSaveEdition(category: Category): void {
    this.editable = false
    this.category = category;
  }


}
