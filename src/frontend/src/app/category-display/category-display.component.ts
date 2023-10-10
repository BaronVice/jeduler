import { Component, Input, Output, EventEmitter } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {Category} from "../category";



@Component({
  selector: 'app-category-display',
  templateUrl: './category-display.component.html',
  styleUrls: ['./category-display.component.css'],
  standalone: true,
  imports: [MatCardModule, MatButtonModule],
})
export class CategoryDisplayComponent {

  @Input() category: Category = new Category(0, "", "");

  @Output() removeItemEvent = new EventEmitter();
  @Output() editItemEvent = new EventEmitter();
}
