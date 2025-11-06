import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioListComponent } from './usuario-list.component';
import {RouterTestingModule} from '@angular/router/testing';

describe('UsuarioListComponent', () => {
  let component: UsuarioListComponent;
  let fixture: ComponentFixture<UsuarioListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        UsuarioListComponent,
        RouterTestingModule,
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuarioListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
