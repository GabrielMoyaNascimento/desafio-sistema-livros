import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {Livro} from '../../../models/livro.model';
import {LivroService} from '../../../services/livro';
import {EmprestimoService} from '../../../services/emprestimo';

@Component({
  standalone: false,
  selector: 'app-livro-list',
  templateUrl: './livro-list.component.html',
  styleUrls: ['./livro-list.component.css']
})
export class LivroListComponent implements OnInit {

  livros$!: Observable<Livro[]>;

  constructor(private livroService: LivroService, private emprestimoService: EmprestimoService) { }

  ngOnInit(): void {
    this.loadLivros();
  }

  loadLivros(): void {
    this.livros$ = this.livroService.getLivros();
  }

  deleteLivro(id: number): void {
    if (confirm('Tem certeza que deseja excluir este livro?')) {
      this.livroService.deleteLivro(id).subscribe({
        next: () => {
          this.loadLivros();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao excluir livro. Verifique se ele não está emprestado.');
        }
      });
    }
  }

  devolverLivro(emprestimoId: number | null): void {
    if (!emprestimoId) {
      alert('Erro: ID do empréstimo não encontrado.');
      return;
    }

    if (confirm('Confirmar a devolução deste livro?')) {
      this.emprestimoService.devolverLivro(emprestimoId).subscribe({
        next: () => {
          this.loadLivros();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao devolver livro: ' + (err.error.message || 'Erro desconhecido.'));
        }
      });
    }
  }
}
