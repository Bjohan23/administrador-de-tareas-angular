import { Project } from './project';

export class Task {
  id: number;
  title: string;
  description: string;
  status: string;

  projectId: number;


  constructor(id: number, title: string, description: string, status: string, projectId: number) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;

    this.projectId = projectId;
  }
}
