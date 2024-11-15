import { Routes } from '@angular/router';
import { TareasComponentComponent } from './components/tareas-component/tareas-component.component';
import { ProyectosComponent } from './components/proyectos/proyectos.component';
import { UsuariosComponent } from './components/usuarios/usuarios.component';

import { LoginComponent } from './components/login/login.component';
export const routes: Routes = [
    { path: 'login', component: LoginComponent },

    { path: 'usuarios', component: UsuariosComponent },
    { path: 'proyectos', component: ProyectosComponent },
    { path: 'tareas', component: TareasComponentComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
