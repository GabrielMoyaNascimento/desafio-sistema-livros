import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Usuario} from '../../../models/usuario.model';
import {Livro} from '../../../models/livro.model';
import {EmprestimoService} from '../../../services/emprestimo';
import {UsuarioService} from '../../../services/usuario';
import {LivroService} from '../../../services/livro';


@Component({
  standalone: false,
  selector: 'app-emprestimo-form',
  templateUrl: './emprestimo-form.component.html',
  styleUrls: ['./emprestimo-form.component.css']
})
export class EmprestimoFormComponent implements OnInit {

  emprestimoForm!: FormGroup;
  usuarios$!: Observable<Usuario[]>;
  livros$!: Observable<Livro[]>;

  constructor(
    private fb: FormBuilder,
    private emprestimoService: EmprestimoService,
    private usuarioService: UsuarioService,
    private livroService: LivroService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadDropdowns();
  }

  initForm(): void {
    this.emprestimoForm = this.fb.group({
      usuarioId: [null, Validators.required],
      livroId: [null, Validators.required],
      dataDevolucaoPrevista: ['', Validators.required]
    });
  }

  loadDropdowns(): void {
    this.usuarios$ = this.usuarioService.getUsuarios();
    this.livros$ = this.livroService.getLivros().pipe(
      map(livros => livros.filter(livro => livro.status === 'DISPONÍVEL'))
    );
  }

  onSubmit(): void {
    if (this.emprestimoForm.invalid) {
      this.emprestimoForm.markAllAsTouched();
      return;
    }

    const request = this.emprestimoForm.value;

    this.emprestimoService.createEmprestimo(request).subscribe({
      next: () => {
        this.router.navigate(['/livros']);
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao realizar empréstimo: ' + err.error.message);
      }
    });
  }
}
