import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Usuario } from "../../../models/usuario.model";
import { UsuarioService } from "../../../services/services/usuario";

@Component({
  standalone: false,
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {

  usuarios$: Observable<Usuario[]> = of([]);

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.loadUsuarios();
  }

  loadUsuarios(): void {
    this.usuarios$ = this.usuarioService.getUsuarios();
  }

  deleteUsuario(id: number): void {
    if (confirm('Tem certeza que deseja excluir este usuário?')) {
      this.usuarioService.deleteUsuario(id).subscribe({
        next: () => {
          alert('Usuário excluído com sucesso!');
          this.loadUsuarios();
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao excluir usuário. Verifique se ele não possui empréstimos ativos.');
        }
      });
    }
  }
}
