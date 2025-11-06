import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecomendacaoView } from './recomendacao-view.component';

describe('RecomendacaoView', () => {
  let component: RecomendacaoView;
  let fixture: ComponentFixture<RecomendacaoView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecomendacaoView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecomendacaoView);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
