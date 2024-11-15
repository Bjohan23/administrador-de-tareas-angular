import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task';
@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl = 'http://localhost:8090/v1/tareas';  // Cambia la URL según tu backend

  constructor(private http: HttpClient) { }

  // Obtener todas las tareas
  getTasks(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }

  // Obtener una tarea por ID
  getTaskById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Crear una nueva tarea
  createTask(task: Task): Observable<any> {
    return this.http.post<any>(this.apiUrl, task);
  }

  // Actualizar una tarea
  updateTask(id: number, task: Task): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, task);
  }

  // Eliminar una tarea
  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
