import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Project } from '../models/project';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ProyectosService {
  private apiUrl = 'http://localhost:8090/v1/project'; // Cambia la URL según tu configuración del backend

  constructor(private http: HttpClient) { }

  // Obtener todos los proyectos
  getProjects(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  // Obtener un proyecto por ID
  getProjectById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Crear un nuevo proyecto
  createProject(project: Project): Observable<any> {
    return this.http.post<any>(this.apiUrl, project);
  }

  // Actualizar un proyecto existente
  updateProject(id: number, project: Project): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, project);
  }

  // Eliminar un proyecto
  deleteProject(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
