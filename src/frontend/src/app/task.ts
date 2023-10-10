import {Category} from "./category";

// TODO: probably want to separate date and time in both backend and frontend
export class Task {
  private id: number | null;
  private name : string;
  private categories : Category[] | null;
  private description: string | null;
  private startsAt: Date | null;
  private expiresAt: Date;
  private notifyAt: Date | null;


  constructor(
    id: number | null,
    name: string, categories: Category[] | null,
    description: string | null,
    startsAt: Date | null,
    expiresAt: Date,
    notifyAt: Date | null
  ) {

    this.id = id;
    this.name = name;
    this.categories = categories;
    this.description = description;
    this.startsAt = startsAt;
    this.expiresAt = expiresAt;
    this.notifyAt = notifyAt;
  }


  getId(): number | null {
    return this.id;
  }

  setId(value: number | null) {
    this.id = value;
  }

  getName(): string {
    return this.name;
  }

  setName(value: string) {
    this.name = value;
  }

  getCategories(): Category[] | null {
    return this.categories;
  }

  setCategories(value: Category[] | null) {
    this.categories = value;
  }

  getDescription(): string | null {
    return this.description;
  }

  setDescription(value: string | null) {
    this.description = value;
  }

  getStartsAt(): Date | null {
    return this.startsAt;
  }

  setStartsAt(value: Date | null) {
    this.startsAt = value;
  }

  getExpiresAt(): Date {
    return this.expiresAt;
  }

  setExpiresAt(value: Date) {
    this.expiresAt = value;
  }

  getNotifyAt(): Date | null {
    return this.notifyAt;
  }

  setNotifyAt(value: Date | null) {
    this.notifyAt = value;
  }
}
