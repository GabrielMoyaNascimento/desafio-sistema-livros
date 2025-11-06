import { TestBed } from '@angular/core/testing';

import { Recomendacao } from './recomendacao';

describe('Recomendacao', () => {
  let service: Recomendacao;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Recomendacao);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
