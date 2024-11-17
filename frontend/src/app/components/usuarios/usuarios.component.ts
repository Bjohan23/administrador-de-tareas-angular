import { Component } from '@angular/core';
import { User } from '../../models/user';
import { CommonModule } from '@angular/common'; 
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; 
import { UsuariosService } from '../../services/usuarios.service';
@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})
export class UsuariosComponent {
  users: User[] = [];
  
  // Declara la variable 'currentDate2' correctamente antes de usarla
  // Declarar la propiedad 'user' y asignar la fecha actual al campo 'createdAt'
  user: User = new User(0, '', '', '', new Date(), []);

  isEditing: boolean = false;

  constructor(private userService: UsuariosService, private router: Router) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  // Cargar los usuarios desde el servicio
  loadUsers(): void {
    this.userService.getUsers().subscribe(response => {
      this.users = response.body;
    });
  }

  // Eliminar un usuario
  deleteUser(id: number): void {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.userService.deleteUser(id).subscribe(() => {
        this.loadUsers();  // Recargar la lista de usuarios
      });
    }
  }

  // Navegar a otras páginas
  navigateTo(path: string): void {
    this.router.navigate([path]);
  }

  // Mostrar el formulario para registrar o editar un usuario
// Método onRegister() para limpiar el formulario de registro
onRegister(): void {
  this.isEditing = false;  // No es modo de edición
  const currentDate = new Date(); 
  this.user = new User(0, '', '', '', currentDate, []);  // Limpiar el formulario
}

// Método onEdit() para editar un usuario existente
onEdit(id: number): void {
  this.isEditing = true;  // Establecer en true para editar
  this.userService.getUserById(id).subscribe(response => {
    this.user = response.body;
  });
}

  // Enviar el formulario de registro o edición
  onSubmit(): void {
    console.log(this.user)
    if (this.isEditing) {
      this.userService.updateUser(this.user.id, this.user).subscribe(() => {
        this.loadUsers();
        this.isEditing = false;  // Ocultar el formulario
      });
    } else {
      this.userService.createUser(this.user).subscribe(() => {
        this.loadUsers();
        this.isEditing = false;  // Ocultar el formulario
      });
    }
  }

  // Cancelar el formulario
  cancel(): void {
    this.isEditing = false;
  }
}
