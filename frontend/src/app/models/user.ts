export class User {
    id: number;
    name: string;
    email: string;
    password: string;
    createdAt: Date;
    projects: any[]; // Puede cambiarse a la clase Project si se tiene definida

    constructor(
        id: number = 0,
        name: string = '',
        email: string = '',
        password: string = '',
        createdAt: Date = new Date(),
        projects: any[] = [] // Puedes cambiarlo a una lista de objetos `Project` si tienes la clase `Project` definida
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.projects = projects;
    }

    // Método para actualizar el nombre
    actualizarNombre(nuevoNombre: string): void {
        this.name = nuevoNombre;
    }

    // Método para actualizar el correo electrónico
    actualizarEmail(nuevoEmail: string): void {
        this.email = nuevoEmail;
    }

    // Método para agregar un proyecto al usuario
    agregarProyecto(proyecto: any): void { // Cambia `any` por la clase `Project` si la tienes definida
        this.projects.push(proyecto);
    }

    // Método para eliminar un proyecto del usuario
    eliminarProyecto(proyectoId: number): void {
        this.projects = this.projects.filter(proyecto => proyecto.id !== proyectoId);
    }
}
