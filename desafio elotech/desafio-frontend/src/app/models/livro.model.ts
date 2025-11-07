export interface Livro {
  id: number;
  titulo: string;
  autor: string;
  isbn: string;
  dataPublicacao: string;
  categoria: string;
  status: 'DISPONÍVEL' | 'EMPRESTADO';
  activeEmprestimoId: number | null;
}
