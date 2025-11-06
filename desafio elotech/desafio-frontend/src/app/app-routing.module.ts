import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UsuarioListComponent} from './components/usuario/usuario-list/usuario-list.component';
import {UsuarioFormComponent} from './components/usuario/usuario-form/usuario-form.component';
import {LivroListComponent} from './components/livro/livro-list/livro-list.component';
import {LivroFormComponent} from './components/livro/livro-form/livro-form.component';
import {EmprestimoFormComponent} from './components/emprestimo/emprestimo-form/emprestimo-form.component';
import {RecomendacaoViewComponent} from './components/recomendacao/recomendacao-view/recomendacao-view.component';

const routes: Routes = [
  { path: 'usuarios', component: UsuarioListComponent },
  { path: 'usuarios/novo', component: UsuarioFormComponent },
  { path: 'usuarios/editar/:id', component: UsuarioFormComponent },
  { path: 'livros', component: LivroListComponent },
  { path: 'livros/novo', component: LivroFormComponent },
  { path: 'livros/editar/:id', component: LivroFormComponent },
  { path: 'emprestimos', component: EmprestimoFormComponent },
  { path: 'recomendacoes', component: RecomendacaoViewComponent },
  { path: '', redirectTo: '/usuarios', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
