import { Component } from '@angular/core';
import { Project } from '../../models/project';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

import { User } from '../../models/user';
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
    project: Project = new Project(0, '', '', 0);
    nombre: string[] = []; 
    listUsuario: User[] = [];
    constructor(
      private usuariosService: UsuariosService, 
      private proyectosService: ProyectosService, 
      private router: Router,
      ) {}


    ngOnInit(): void {
      this.loadProjects();
      this.cargaUsuarios();

    }

  cargaUsuarios(){
    this.usuariosService.getUsers().subscribe(response =>{
      this.listUsuario = response.body;
    })
    console.log( this.listUsuario);
  }
  // Cargar todos los proyectos
  loadProjects(): void {
    this.proyectosService.getProjects().subscribe(response => {
      this.projects = response.body; 
      console.log( this.projects);
      this.projects.forEach((project, index) => {
        this.usuariosService.getUserById(project.userId).subscribe(userResponse => {
          this.nombre[index]= (userResponse.body?.name); // Guardamos el nombre del cliente en una propiedad nueva del proyecto
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
    this.isEditing = false;

    this.project = new Project(0, '', '', 0);  // Limpiar el formulario
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
    this.project.userId = +this.project.userId;
    console.log(this.project);  
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
