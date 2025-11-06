import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {Usuario} from '../../../models/usuario.model';
import {Livro} from '../../../models/livro.model';
import {UsuarioService} from '../../../services/services/usuario';
import {RecomendacaoService} from '../../../services/services/recomendacao';


@Component({
  standalone: false,
  selector: 'app-recomendacao-view',
  templateUrl: './recomendacao-view.component.html',
  styleUrls: ['./recomendacao-view.component.css']
})
export class RecomendacaoViewComponent implements OnInit {

  usuarios$!: Observable<Usuario[]>;
  recomendacoes$: Observable<Livro[]> | null = null;
  selectedUserId: number | null = null;
  isLoading = false;

  constructor(
    private usuarioService: UsuarioService,
    private recomendacaoService: RecomendacaoService
  ) { }

  ngOnInit(): void {
    this.usuarios$ = this.usuarioService.getUsuarios();
  }

  buscarRecomendacoes(): void {
    if (this.selectedUserId) {
      this.isLoading = true;
      this.recomendacoes$ = this.recomendacaoService.getRecomendacoes(this.selectedUserId);

      this.recomendacoes$.subscribe(() => this.isLoading = false);
    }
  }
}
