import { Livro } from './livro.model';
import { Usuario } from './usuario.model';

export enum StatusEmprestimo {
  ATIVO = 'ATIVO',
  DEVOLVIDO = 'DEVOLVIDO'
}

export interface UsuarioSimplificado {
  id: number;
  nome: string;
  email: string;
}

export interface LivroSimplificado {
  id: number;
  titulo: string;
  autor: string;
}

export interface Emprestimo {
  id: number;
  dataEmprestimo: string;
  dataDevolucao: string;
  status: StatusEmprestimo;
  usuario: UsuarioSimplificado;
  livro: LivroSimplificado;
}


export interface EmprestimoCreateRequest {
  usuarioId: number;
  livroId: number;
  dataDevolucaoPrevista: string; // ex: "2025-11-19"
}
