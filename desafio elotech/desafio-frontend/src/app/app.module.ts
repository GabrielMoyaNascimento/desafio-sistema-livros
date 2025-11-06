import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {UsuarioListComponent} from './components/usuario/usuario-list/usuario-list.component';
import {UsuarioFormComponent} from './components/usuario/usuario-form/usuario-form.component';
import {LivroListComponent} from './components/livro/livro-list/livro-list.component';
import {LivroFormComponent} from './components/livro/livro-form/livro-form.component';
import {EmprestimoFormComponent} from './components/emprestimo/emprestimo-form/emprestimo-form.component';
import {RecomendacaoViewComponent} from './components/recomendacao/recomendacao-view/recomendacao-view.component';

@NgModule({
  declarations: [
    AppComponent,
    UsuarioListComponent,
    UsuarioFormComponent,
    LivroListComponent,
    LivroFormComponent,
    EmprestimoFormComponent,
    RecomendacaoViewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
