import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Livro} from '../../models/livro.model';

@Injectable({
  providedIn: 'root'
})
export class RecomendacaoService {

  private apiUrl = '/api/recomendacoes';

  constructor(private http: HttpClient) { }

  getRecomendacoes(usuarioId: number): Observable<Livro[]> {
    const params = new HttpParams().set('usuarioId', usuarioId.toString());

    return this.http.get<Livro[]>(this.apiUrl, { params: params });
  }

}
