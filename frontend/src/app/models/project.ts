export class Project {
    id: number;
    name: string;
    description: string;
    userId: number;
    createdAt: Date;
  
    constructor(id: number, name: string, description: string, userId: number, createdAt: Date) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.userId = userId;
      this.createdAt = createdAt;
    }
  }
  