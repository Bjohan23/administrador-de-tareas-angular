import { Component } from '@angular/core';
import { Project } from '../../models/project';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { ProyectosService } from '../../services/proyectos.service';
import { UsuariosService } from '../../services/usuarios.service';
@Component({
  selector: 'app-proyectos',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './proyectos.component.html',
  styleUrl: './proyectos.component.css'
})
export class ProyectosComponent {
  projects: Project[] = [];
  isEditing: boolean = false;
  project: Project = new Project(0, '', '', 0, new Date());
  nombre: string = '';
  constructor(private usuariosService: UsuariosService, private proyectosService: ProyectosService, private router: Router) {}

  ngOnInit(): void {
    this.loadProjects();
  }

  // Cargar todos los proyectos
  loadProjects(): void {
    this.proyectosService.getProjects().subscribe(response => {
      this.projects = response.body; 
      this.projects.forEach((project) => {
        this.usuariosService.getUserById(project.userId).subscribe(userResponse => {
          this.nombre = userResponse.body?.name; // Guardamos el nombre del cliente en una propiedad nueva del proyecto
        });
      });// Suponiendo que la respuesta tiene un cuerpo con los proyectos
    });
  }

  // Eliminar un proyecto
  deleteProject(id: number): void {
    if (confirm('¿Estás seguro de eliminar este proyecto?')) {
      this.proyectosService.deleteProject(id).subscribe(() => {
        this.loadProjects();  // Recargar la lista de proyectos
      });
    }
  }

  // Navegar a otras páginas
  navigateTo(path: string): void {
    this.router.navigate([path]);
  }

  // Mostrar el formulario para registrar o editar un proyecto
  onRegister(): void {
    this.isEditing = false;  // No es modo de edición
    this.project = new Project(0, '', '', 0, new Date());  // Limpiar el formulario
  }

  // Método para editar un proyecto
  onEdit(id: number): void {
    this.isEditing = true;  // Establecer en true para editar
    this.proyectosService.getProjectById(id).subscribe(response => {
      this.project = response.body;
    });
  }

  // Enviar el formulario de registro o edición
  onSubmit(): void {
    if (this.isEditing) {
      this.proyectosService.updateProject(this.project.id, this.project).subscribe(() => {
        this.loadProjects();
        this.isEditing = false;  // Ocultar el formulario
      });
    } else {
      this.proyectosService.createProject(this.project).subscribe(() => {
        this.loadProjects();
        this.isEditing = false;  // Ocultar el formulario
      });
    }
  }

  // Cancelar el formulario
  cancel(): void {
    this.isEditing = false;
  }
}
