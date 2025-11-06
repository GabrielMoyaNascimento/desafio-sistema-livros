import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {UsuarioService} from '../../../services/services/usuario';


@Component({
  standalone: false,
  selector: 'app-usuario-form',
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {

  usuarioForm!: FormGroup;
  isEditMode = false;
  usuarioId: number | null = null;
  formTitle = 'Novo Usuário';

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.usuarioId = +params['id'];
        this.formTitle = 'Editar Usuário';
        this.loadUsuario(this.usuarioId);
      }
    });
  }

  initForm(): void {
    this.usuarioForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefone: ['', Validators.required],
      dataCadastro: [new Date().toISOString().split('T')[0], Validators.required]
    });
  }

  loadUsuario(id: number): void {
    this.usuarioService.getUsuarioById(id).subscribe(usuario => {
      this.usuarioForm.patchValue(usuario);
    });
  }

  onSubmit(): void {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      return;
    }

    const formValue = this.usuarioForm.value;

    if (this.isEditMode) {
      this.usuarioService.updateUsuario(this.usuarioId!, formValue).subscribe({
        next: () => {
          alert('Usuário atualizado com sucesso!');
          this.router.navigate(['/usuarios']);
        },
        error: (err) => alert('Erro ao atualizar: ' + err.error.message)
      });
    } else {
      this.usuarioService.createUsuario(formValue).subscribe({
        next: () => {
          alert('Usuário criado com sucesso!');
          this.router.navigate(['/usuarios']);
        },
        error: (err) => alert('Erro ao criar: ' + err.error.message)
      });
    }
  }
}
