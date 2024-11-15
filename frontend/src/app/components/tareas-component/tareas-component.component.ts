import { Component } from '@angular/core';
import { Task } from '../../models/task';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { TaskService } from '../../services/task.service';
import { ProyectosService } from '../../services/proyectos.service';  // Asegúrate de tener este servicio
import { Router } from '@angular/router';

@Component({
  selector: 'app-tareas-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tareas-component.component.html',
  styleUrls: ['./tareas-component.component.css']
})
export class TareasComponentComponent {
  tasks: Task[] = [];
  projects: any[] = [];
  filteredTasks: Task[] = [];
  currentTask: Task | null = null;  // Se asegura de que la tarea se inicie como null
  projectFilter: string = '';  // Variable para almacenar el filtro del proyecto

  constructor(
    private taskService: TaskService,
    private projectService: ProyectosService,  // Inyectamos el servicio de proyectos
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTasksAndProjects();
  }

  loadTasksAndProjects(): void {
    // Cargar tareas y proyectos
    this.taskService.getTasks().subscribe((taskResponse) => {
      this.tasks = taskResponse.body;  // Supón que la respuesta tiene un body con las tareas
      this.filteredTasks = this.tasks; // Inicializamos filteredTasks con todas las tareas
    });

    this.projectService.getProjects().subscribe((projectResponse) => {
      this.projects = projectResponse.body;  // Supón que la respuesta tiene un body con los proyectos
    });
  }

  // Filtrar tareas por proyecto
  onProjectFilterChange(): void {
    if (this.projectFilter) {
      this.filteredTasks = this.tasks.filter(task => task.projectId === parseInt(this.projectFilter));
    } else {
      this.filteredTasks = this.tasks;  // Si no hay filtro, mostramos todas las tareas
    }
  }

  // Editar tarea
  onEdit(id: number): void {
    this.router.navigate([`/editar-tarea/${id}`]);  // Navegar a la página de edición de la tarea
  }

  // Eliminar tarea
  deleteTask(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.taskService.deleteTask(id).subscribe(() => {
        this.loadTasksAndProjects(); // Recargar la lista después de eliminar
      });
    }
  }

  // Crear o editar tarea
  createTask(): void {
    this.taskService.createTask(this.currentTask!).subscribe(() => {
      this.loadTasksAndProjects();
      this.resetForm();
    });
  }

  updateTask(): void {
    if (this.currentTask) {
      this.taskService.updateTask(this.currentTask?.id, this.currentTask!).subscribe(() => {
        this.loadTasksAndProjects();
        this.resetForm();
      });
    } else {
    console.error('No hay tarea para crear');
  }

  }

  // Resetear formulario
  resetForm(): void {
    this.currentTask = null;
  }

  // Navegar a otras páginas
  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
