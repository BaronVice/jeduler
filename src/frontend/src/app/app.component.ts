import { Component } from '@angular/core';
import {Task} from "./task";
import {Category} from "./category";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // tasks: Task[] = [];
  categories: Category[] = [];
  title: string;

  constructor(private http: HttpClient){
    this.title = "frontend"
  }

  ngOnInit(): void {
    // this.http.get<Task[]>(
    //   "http://localhost:8080/jeduler/tasks"
    // ).subscribe(data => this.tasks = data);

    this.http.get<Category[]>(
      "http://localhost:8080/jeduler/category"
    ).subscribe(data => this.categories = data);
  }

  appendCategory(newCategory: any): void {
    this.categories.push(newCategory);
  }

  removeCategory(categoryId: number): void {
    this.http.delete(
      "http://localhost:8080/jeduler/category/" + categoryId,
    ).subscribe(data => this.categories = this.categories.filter(
      (category: Category) => categoryId != category.id
    ));
  }
}
