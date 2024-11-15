import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms'; 
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private loginService: LoginService, private router: Router) {}
  goToRegister() {
    this.router.navigate(['/usuarios']);  // Corregí la ruta a '/register'
  }
  onSubmit() {
    if (!this.email || !this.password ) {
      this.errorMessage = 'Todos los campos son obligatorios';
      return;
    }

    this.loginService.login(this.email).subscribe({
      next: (response) => {
        console.log(response.body.password);
        console.log(this.password);

        if (response.ok && response.body.password === this.password) {
          this.errorMessage = ''; 
        }else{
          this.errorMessage = 'Credenciales inválidas';
        }

        this.router.navigate(['/usuarios']);
      },
      error: (error) => {
 
        this.errorMessage = 'Credenciales inválidas';
      }
    });
  }
}
