import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { Observable } from 'rxjs'; 
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl = 'http://localhost:8090/v1/usuarios/email/';

  constructor(private http: HttpClient) { }

  login(email: string): Observable<any> {
    const url = `${this.baseUrl}${email}`;  // Asegúrate de que la URL sea correcta
    return this.http.get<any>(url);  // El tipo de la respuesta es 'any' en este caso, puedes ajustarlo según tu backend
  }
}
