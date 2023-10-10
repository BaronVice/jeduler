import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';

import {Category} from '../category';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.css'],
  standalone: true,
  imports: [MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    FormsModule,
    MatCardModule,
    MatCheckboxModule,
    MatRadioModule],
})
export class CategoryEditComponent {

  @Input() category: Category = new Category(0, "", "");

  @Output() editDataEvent = new EventEmitter();

  constructor(private http: HttpClient) {}

  onSubmit(): void {
    this.http.post<Category>(
      "http://localhost:8080/category",
      this.category
    ).subscribe(data => {
      this.editDataEvent.emit(data);
    });
  }
}
