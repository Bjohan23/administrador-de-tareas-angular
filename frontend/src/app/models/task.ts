import { Project } from './project';

export class Task {
  id: number;
  title: string;
  description: string;
  status: string;
  project: Project; 
  projectId: number;
  createdAt: string;

  constructor(id: number, title: string, description: string, status: string, project: Project, projectId: number, createdAt: string) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.project = project;
    this.projectId = projectId;
    this.createdAt = createdAt;
  }
}
