import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecomendacaoViewComponent } from './recomendacao-view.component';

describe('RecomendacaoViewComponent', () => {
  let component: RecomendacaoViewComponent;
  let fixture: ComponentFixture<RecomendacaoViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecomendacaoViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecomendacaoViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
