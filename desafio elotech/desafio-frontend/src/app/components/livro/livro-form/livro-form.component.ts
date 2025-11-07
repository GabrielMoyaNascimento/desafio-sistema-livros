import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {LivroService} from '../../../services/livro';


@Component({
  standalone: false,
  selector: 'app-livro-form',
  templateUrl: './livro-form.component.html',
  styleUrls: ['./livro-form.component.css']
})
export class LivroFormComponent implements OnInit {

  livroForm!: FormGroup;
  isEditMode = false;
  livroId: number | null = null;
  formTitle = 'Novo Livro';

  constructor(
    private fb: FormBuilder,
    private livroService: LivroService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.livroId = +params['id'];
        this.formTitle = 'Editar Livro';
        this.loadLivro(this.livroId);
      }
    });
  }

  initForm(): void {
    this.livroForm = this.fb.group({
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      isbn: ['', Validators.required],
      dataPublicacao: [new Date().toISOString().split('T')[0], Validators.required],
      categoria: ['', Validators.required]
    });
  }

  loadLivro(id: number): void {
    this.livroService.getLivroById(id).subscribe(livro => {
      this.livroForm.patchValue(livro);
    });
  }

  onSubmit(): void {
    if (this.livroForm.invalid) {
      this.livroForm.markAllAsTouched();
      return;
    }

    const formValue = this.livroForm.value;

    const request = this.isEditMode
      ? this.livroService.updateLivro(this.livroId!, formValue)
      : this.livroService.createLivro(formValue);

    request.subscribe({
      next: () => {
        this.router.navigate(['/livros']);
      },
      error: (err) => alert('Erro ao salvar: ' + err.error.message)
    });
  }
}
