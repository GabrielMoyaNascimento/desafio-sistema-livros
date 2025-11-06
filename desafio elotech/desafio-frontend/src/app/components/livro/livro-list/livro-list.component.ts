import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {Livro} from '../../../models/livro.model';
import {LivroService} from '../../../services/services/livro';

// Imports Standalone REMOVIDOS (CommonModule, RouterLink)

@Component({
  standalone: false,
  selector: 'app-livro-list',
  templateUrl: './livro-list.component.html',
  styleUrls: ['./livro-list.component.css']
})
export class LivroListComponent implements OnInit {

  livros$!: Observable<Livro[]>;

  constructor(private livroService: LivroService) { }

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
          alert('Livro excluído com sucesso!');
          this.loadLivros();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao excluir livro. Verifique se ele não está emprestado.');
        }
      });
    }
  }
}
