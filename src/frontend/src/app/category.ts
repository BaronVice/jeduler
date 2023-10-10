export class Category {

  constructor(
    public id: number | null,
    public name: string,
    public color: string
  ) {
    this.id = id;
    this.name = name;
    this.color = color;
  }
}
