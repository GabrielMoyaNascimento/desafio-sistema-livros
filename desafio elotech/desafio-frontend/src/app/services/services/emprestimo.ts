import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Emprestimo, EmprestimoCreateRequest} from '../../models/emprestimo.model';

@Injectable({
  providedIn: 'root'
})
export class EmprestimoService {

  private apiUrl = '/api/emprestimos';

  constructor(private http: HttpClient) { }

  createEmprestimo(request: EmprestimoCreateRequest): Observable<Emprestimo> {
    return this.http.post<Emprestimo>(this.apiUrl, request);
  }

  devolverLivro(id: number): Observable<Emprestimo> {
    return this.http.patch<Emprestimo>(`${this.apiUrl}/${id}/devolver`, {});
  }

}
