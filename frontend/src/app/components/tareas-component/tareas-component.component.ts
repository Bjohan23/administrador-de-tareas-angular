import { Component } from '@angular/core';
import { Task } from '../../models/task';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../../services/task.service';
import { ProyectosService } from '../../services/proyectos.service'; // Asegúrate de tener este servicio
import { Router } from '@angular/router';
import { Project } from '../../models/project'; // Asegúrate de importar Project

@Component({
  selector: 'app-tareas-component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tareas-component.component.html',
  styleUrls: ['./tareas-component.component.css']
})
export class TareasComponentComponent {
  tasks: Task[] = [];
  projects: Project[] = [];  // Definir el array de proyectos
  filteredTasks: Task[] = [];
  currentTask: Task = new Task(0,'','','PENDIENTE', 0 );  // Se asegura de que la tarea se inicie como null
  projectFilter: string = '';  // Variable para almacenar el filtro del proyecto
  taskName: string[] = []; // Array para almacenar los nombres de proyectos asociados a tareas

  constructor(
    private taskService: TaskService,
    private projectService: ProyectosService,  // Inyectamos el servicio de proyectos
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTasksAndProjects();
    this.loadName();
  }
  
  loadName(){
    this.tasks.forEach((task, index) => {
      this.projectService.getProjectById(task.projectId).subscribe((response)=> {
        this.taskName[index] = response.body.name;
      });
    });
  }
  loadTasksAndProjects(): void {
    // Cargar tareas
    this.taskService.getTasks().subscribe((taskResponse) => {
      this.tasks = taskResponse.body;  // Supón que la respuesta tiene un body con las tareas
      console.log(this.tasks   )
    });
    

    // Cargar proyectos
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
    // Buscar la tarea por id y cargarla en currentTask
    this.taskService.getTaskById(id).subscribe(task => {
      this.currentTask = task.body; // Asumiendo que la respuesta tiene un body con la tarea
    });
  }

  // Eliminar tarea
  deleteTask(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta tarea?')) {
      this.taskService.deleteTask(id).subscribe(() => {
        this.loadTasksAndProjects(); // Recargar la lista después de eliminar
      });
    }
  }
  createNewTask(): void{
    this.currentTask = new Task(
      0,                      // ID vacío para la nueva tarea
      '',                     // Título vacío
      '',                     // Descripción vacía
      'PENDIENTE',                 
      0
    );
  }
  // Crear o editar tarea
  createTask(): void {
    if (this.currentTask) {
      let numero = +this.currentTask?.projectId;
      this.currentTask.projectId = numero;
      
      this.taskService.createTask(this.currentTask!).subscribe(() => {
        this.loadTasksAndProjects();
        this.resetForm();
      });
    }else {
      console.error("currentTask is null or undefined");
    }
  }

  updateTask(): void {
    if (this.currentTask) {
      // Llamada al servicio para actualizar la tarea existente
      this.taskService.updateTask(this.currentTask?.id, this.currentTask!).subscribe(() => {
        this.loadTasksAndProjects();
        this.resetForm();
      });
    } else {
      console.error('No hay tarea para actualizar');
    }
  }

  // Resetear formulario
  resetForm(): void {
    this.currentTask = new Task(0,'','','', 0 );
  }

  // Navegar a otras páginas
  navigateTo(path: string): void {
    this.router.navigate([path]);
  }
}
